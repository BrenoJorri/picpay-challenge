package com.picpay.desafio.android.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.Resource
import com.picpay.desafio.android.core.base.BaseViewModel
import com.picpay.desafio.android.domain.model.UserDomain
import com.picpay.desafio.android.domain.usecase.UsersUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class GetUsersViewModel(
    private val useCase: UsersUseCase
) : BaseViewModel() {

    private val mutableUsersLiveData = MutableLiveData<Resource<List<UserDomain>>>()

    val usersLiveData: LiveData<Resource<List<UserDomain>>> by lazy {
        mutableUsersLiveData
    }

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            useCase
                .getUsers()
                .onStart { mutableUsersLiveData.loading() }
                .catch { mutableUsersLiveData.error(it) }
                .collectLatest { users ->
                    mutableUsersLiveData.success(users)
                }
        }
    }

}
