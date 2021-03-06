package com.picpay.desafio.android.core.base

interface BaseMapper {

    interface ToDomain<ENTITY, DOMAIN> {
        fun toDomain(entity: ENTITY): DOMAIN
    }

    interface ToEntity<ENTITY, DOMAIN> {
        fun toEntity(domain: DOMAIN): ENTITY
    }

}
