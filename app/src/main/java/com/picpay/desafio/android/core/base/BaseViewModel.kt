package com.picpay.desafio.android.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.core.Resource

abstract class BaseViewModel : ViewModel() {

    protected fun <T> MutableLiveData<Resource<T>>.success(data: T?) {
        postValue(Resource.success(data))
    }

    protected fun <T> MutableLiveData<Resource<T>>.error(e: Throwable?) {
        value = Resource.error(e)
    }

    protected fun <T> MutableLiveData<Resource<T>>.forceUpdate(data: T?) {
        postValue(Resource.forceUpdate(data))
    }

    protected fun <T> MutableLiveData<Resource<T>>.empty(data: T?) {
        postValue(Resource.empty(data))
    }

    protected fun <T> MutableLiveData<Resource<T>>.loading() {
        value = Resource.loading()
    }
}