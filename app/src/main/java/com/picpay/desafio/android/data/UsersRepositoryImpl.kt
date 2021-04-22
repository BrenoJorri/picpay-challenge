package com.picpay.desafio.android.data

import com.picpay.desafio.android.core.base.BaseRepository
import com.picpay.desafio.android.data.local.dao.UserEntityDao
import com.picpay.desafio.android.data.mapper.UsersMapper
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UsersRepositoryImpl(
    private val api: PicPayService,
    private val mapper: UsersMapper,
    private val userEntityDao: UserEntityDao,
    private val flowOn: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository(), UsersRepository {

    override suspend fun getUsers(): Flow<List<UserDomain>?> =
        flow {
            val result = networkBoundResource(
                makeApiCall = {
                    val users = api.getUsers()
                    mapper.mapResponseToEntity(users)
                },
                saveCallResult = { response -> userEntityDao.insert(response) },
                shouldFetch = { user -> user.isNullOrEmpty() }, //Aplica regra de negocio da empresa
                loadFromDb = { userEntityDao.getAll() }
            ).transform { user -> mapper.toDomain(user) }

            emit(result)
        }.flowOn(flowOn)

}
