package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.core.base.BaseMapper
import com.picpay.desafio.android.data.local.entity.UserEntity
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserDomain

class UsersMapper : BaseMapper.ToDomain<List<UserEntity>, List<UserDomain>> {


    override fun toDomain(entity: List<UserEntity>): List<UserDomain> =
        entity.map { userEntity ->
            UserDomain(
                userEntity.picture,
                userEntity.username,
                userEntity.name
            )
        }


    fun mapResponseToEntity(entity: List<User>): List<UserEntity>? =
        entity.map { userEntity ->
            UserEntity(
                userEntity.img,
                userEntity.username,
                userEntity.name
            )
        }

}
