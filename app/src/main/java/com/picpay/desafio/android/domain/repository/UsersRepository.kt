package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.domain.model.UserDomain
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getUsers(): Flow<List<UserDomain>?>

}
