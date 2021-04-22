package com.picpay.desafio.android.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    val picture: String,
    val username: String,
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
