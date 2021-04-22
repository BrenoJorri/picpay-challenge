package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.domain.repository.UsersRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UsersUseCaseImplTest {


    @MockK
    private lateinit var repository: UsersRepository
    private lateinit var useCase: UsersUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UsersUseCaseImpl(repository)
    }

    @Test
    fun `Should retrieve users with success`() = runBlockingTest {
        val userDomainMock = mockk<List<UserDomain>>(relaxed = true)
        val userDomainFlow = flow {
            emit(userDomainMock)
        }


        coEvery { repository.getUsers() }.returns(userDomainFlow)

        val users = useCase.getUsers()

        coVerify(exactly = 1) { repository.getUsers() }
        assertEquals(repository.getUsers(), users)
    }
}