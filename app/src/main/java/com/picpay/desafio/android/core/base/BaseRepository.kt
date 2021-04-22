package com.picpay.desafio.android.core.base

abstract class BaseRepository {

    suspend fun <T> networkBoundResource(
        saveCallResult: suspend (T) -> Unit = {},
        shouldFetch: (T?) -> Boolean = { true },
        makeApiCall: suspend () -> T,
        loadFromDb: (suspend () -> T)? = null
    ): T? {

        val dataFromDb = loadFromDb?.let { it() }

        return if (shouldFetch(dataFromDb)) {
            try {
                makeApiCall.invoke()?.let { result ->
                    saveCallResult(result)
                    result
                }
            } catch (t: Throwable) {
                throw t
            }
        } else {
            dataFromDb
        }
    }

    fun <Entity, Domain> Entity?.transform(transform: (Entity) -> Domain): Domain? {
        return this?.let { transform.invoke(it) }
    }
}
