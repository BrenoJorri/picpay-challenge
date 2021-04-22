package com.picpay.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.observe
import com.picpay.desafio.android.core.Resource
import com.picpay.desafio.android.data.UsersRepositoryImpl
import com.picpay.desafio.android.data.local.dao.UserEntityDao
import com.picpay.desafio.android.data.local.entity.UserEntity
import com.picpay.desafio.android.data.mapper.UsersMapper
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.remote.model.User
import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.domain.repository.UsersRepository
import com.picpay.desafio.android.domain.usecase.UsersUseCase
import com.picpay.desafio.android.domain.usecase.UsersUseCaseImpl
import com.picpay.desafio.android.presentation.GetUsersViewModel
import com.picpay.desafio.android.rule.instantLiveDataAndCoroutineRules
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShowUsersFeature {
    @get:Rule
    val rule = instantLiveDataAndCoroutineRules

    private lateinit var dao: UserEntityDao
    private lateinit var api: PicPayService
    private lateinit var mapper: UsersMapper
    private lateinit var repository: UsersRepository
    private lateinit var useCase: UsersUseCase
    private lateinit var uiController: SpyUiController
    private lateinit var viewModel: GetUsersViewModel

    @Before
    fun setUp() {
        api = FakeApi()
        dao = FakeDao()
        mapper = UsersMapper()
        repository = UsersRepositoryImpl(api, mapper, dao)
        useCase = UsersUseCaseImpl(repository)
        viewModel = GetUsersViewModel(useCase)
        uiController = SpyUiController().also {
            it.searcher = viewModel
        }
        uiController.onCreate()
        mockkObject(Resource)
    }

    @Test
    fun performGetUsers() = runBlocking {

        viewModel.getUsers()

        verify(exactly = 1) { Resource.loading<Nothing>() }
        verify(exactly = 1) { Resource.success<List<UserDomain>>(any()) }
    }


    class SpyUiController : LifecycleOwner {

        private lateinit var lifecycleRegistry: LifecycleRegistry

        val renderedStates = mutableListOf<Resource<List<UserDomain>>>()

        lateinit var searcher: GetUsersViewModel

        fun submit() {
            searcher.getUsers()
        }

        fun onCreate() {
            lifecycleRegistry = LifecycleRegistry(this)
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
            searcher.usersLiveData.observe(this) {
                renderedStates.add(it)
            }
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }
    }


    class FakeApi : PicPayService {
        override suspend fun getUsers(): List<User> = listOf(User("", "", 1, ""))
    }

    class FakeDao : UserEntityDao {
        override suspend fun insert(users: List<UserEntity>?) = Unit

        override fun getAll(): List<UserEntity> =
            listOf(UserEntity("", "", "", 1))
    }
}