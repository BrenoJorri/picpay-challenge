package com.picpay.desafio.android.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.data.local.dao.UserEntityDao
import com.picpay.desafio.android.data.local.entity.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PicpayDatabaseTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var db: PicpayDatabase
    private lateinit var dao: UserEntityDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(context, PicpayDatabase::class.java).build()
        dao = db.userEntityDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getAll_ShouldReturnListOfUsers() = runBlocking {
        val users = dao.getAll()

        assertEquals(emptyList<UserEntity>(), users)
    }

    @Test
    fun insert_ShouldInsertListUsers() = runBlocking {
        dao.insert(USERS_ENTITY)

        val users = dao.getAll()
        assertEquals(USERS_ENTITY, users)
    }

    companion object {
        private val USERS_ENTITY = listOf(
            UserEntity(
                "url",
                "username1",
                "name1",
                1
            )
        )
    }
}