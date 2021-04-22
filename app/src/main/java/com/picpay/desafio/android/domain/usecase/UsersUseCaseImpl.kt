package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow

class UsersUseCaseImpl(
    private val repository: UsersRepository
) : UsersUseCase {

    override suspend fun getUsers(): Flow<List<UserDomain>?> =
        repository.getUsers()

}
