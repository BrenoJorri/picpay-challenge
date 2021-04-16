package com.picpay.desafio.android.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

sealed class Resource<T> {
    data class Success<T>(val data: T?) : Resource<T>()
    data class Error<T>(val throwable: Throwable?) : Resource<T>()
    data class ForceUpdate<T>(val data: T?) : Resource<T>()
    data class Empty<T>(val data: T?) : Resource<T>()
    class Loading<T> : Resource<T>()

    companion object {
        fun <T> success(data: T?): Resource<T> = Success(data)
        fun <T> error(throwable: Throwable?): Resource<T> = Error(throwable)
        fun <T> forceUpdate(data: T?): Resource<T> = ForceUpdate(data)
        fun <T> empty(data: T?): Resource<T> = Empty(data)
        fun <T> loading(): Resource<T> = Loading()
    }
}

fun <T> LiveData<Resource<T>>.observeResource(
    owner: LifecycleOwner,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit,
    onForceUpdate: (T) -> Unit = {},
    onLoading: () -> Unit = {},
    onEmpty: (T) -> Unit = {}
) {

    observe(owner, Observer { resource ->
        when (resource) {
            is Resource.Loading -> onLoading.invoke()
            is Resource.Success -> resource.data?.let { onSuccess.invoke(it) }
            is Resource.Error -> resource.throwable?.let { onError.invoke(it) }
            is Resource.ForceUpdate -> resource.data?.let { onForceUpdate.invoke(it) }
            is Resource.Empty -> resource.data?.let { onEmpty.invoke(it) }
        }
    })
}