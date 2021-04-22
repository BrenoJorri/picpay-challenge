package com.picpay.desafio.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.local.dao.UserEntityDao
import com.picpay.desafio.android.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class PicpayDatabase: RoomDatabase() {

    abstract val userEntityDao: UserEntityDao
}