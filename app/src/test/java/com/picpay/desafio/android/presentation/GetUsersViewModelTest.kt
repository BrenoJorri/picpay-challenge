package com.picpay.desafio.android.presentation

import com.picpay.desafio.android.core.Resource
import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.domain.usecase.UsersUseCase
import com.picpay.desafio.android.rule.instantLiveDataAndCoroutineRules
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetUsersViewModelTest {

    @get:Rule
    val rule = instantLiveDataAndCoroutineRules

    @MockK
    private lateinit var useCase: UsersUseCase

    private lateinit var viewModel: GetUsersViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkObject(Resource)
    }

    @Test
    fun `Should retrieve users with success`() {
        val userDomain = userDomainListMock
        val userFlow = flow { emit(userDomain) }
        coEvery { useCase.getUsers() }.returns(userFlow)
        viewModel = GetUsersViewModel(useCase)
        verify(exactly = 1) { Resource.loading<Nothing>() }
        verify(exactly = 1) { Resource.success(userDomain) }
        verify(exactly = 0) { Resource.error<Throwable>(any()) }
        viewModel.usersLiveData.observeForever {
            assertEquals(viewModel.usersLiveData.value, Resource.success(userDomain))
        }
    }

    @Test
    fun `Should retrieve users with error`() {
        val exception = Exception()

        coEvery { useCase.getUsers() } returns flow { throw exception }

        viewModel = GetUsersViewModel(useCase)

        verify(exactly = 0) { Resource.success<Any>(any()) }
        verify(exactly = 1) { Resource.loading<Nothing>() }
        verify(exactly = 1) { Resource.error<Throwable>(exception) }

        viewModel.usersLiveData.observeForever {
            assertEquals(viewModel.usersLiveData.value, Resource.error<Throwable>(exception))
        }
    }

    companion object {
        val userDomainListMock = listOf(
            UserDomain("imageUrl1", "username1", "name1"),
            UserDomain("imageUrl2", "username2", "name2"),
            UserDomain("imageUrl3", "username3", "name3")
        )
    }
}