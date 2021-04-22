package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.local.dao.UserEntityDao
import com.picpay.desafio.android.data.mapper.UsersMapper
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.domain.repository.UsersRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UsersRepositoryImplTest {

    @MockK
    private lateinit var mapper: UsersMapper

    @MockK
    private lateinit var api: PicPayService

    @MockK
    private lateinit var dao: UserEntityDao

    private lateinit var repository: UsersRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UsersRepositoryImpl(api, mapper, dao)
    }

    @Test
    fun `Should return users with success`() = runBlocking {
        val response = mockk<List<User>>(relaxed = true)
        val domain = mockk<UserDomain>(relaxed = true)
        val domainList = listOf(domain)

        every { mapper.mapResponseToEntity(response) }.returns(emptyList())
        every { mapper.toDomain(emptyList()) }.returns(domainList)
        coEvery { dao.insert(any()) } returns Unit
        coEvery { api.getUsers() }.returns(response)
        every { dao.getAll() }.returns(emptyList())

        val users = repository.getUsers()
        val user = users.first()

        assertEquals(domain, user?.first())
        verify(exactly = 1) { mapper.toDomain(emptyList()) }
        verify(exactly = 1) { dao.getAll() }
        coVerify(exactly = 1) { api.getUsers() }
    }
}