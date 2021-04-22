package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

interface UsersUseCase {


    suspend fun getUsers(): Flow<List<UserDomain>?>
}
