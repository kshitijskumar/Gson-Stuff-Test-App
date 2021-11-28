package com.example.gsonstuffapp.random

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class SessionCacheStuff {

    private val cacheStuff = mutableMapOf<String ,Any?>()

    suspend fun <T>execute(
        apiCall: suspend () -> Response<T>,
        cacheType: CacheType,
        key: String
    ) = flow<T> {

        when(cacheType) {
            CacheType.Network -> {
                val response = apiCall.invoke()
                if (response.isSuccessful && response.body() != null) {
                    Log.d("CacheStuff", "from pure network from ${Thread.currentThread().name}")
                    emit(response.body()!!)
                }
            }
            CacheType.Cache -> {
                if (cacheStuff[key] != null) {
                    Log.d("CacheStuff", "from cache")
                    emit(cacheStuff[key] as T)
                } else {
                    val response = apiCall.invoke()
                    if (response.isSuccessful && response.body() != null) {
                        emit(response.body()!!)
                        Log.d("CacheStuff", "from cache first network")
                        cacheStuff[key] = response.body()
                    }
                }
            }
            CacheType.LazyNetwork -> {
                if (cacheStuff[key] != null) {
                    Log.d("CacheStuff", "initial from cache")
                    emit(cacheStuff[key] as T)
                }
                val response = apiCall.invoke()
                if (response.isSuccessful && response.body() != null) {
                    emit(response.body()!!)
                    Log.d("CacheStuff", "emitting updated")
                    cacheStuff[key] = response.body()
                }
            }
        }

    }
        .flowOn(Dispatchers.IO)
        .catch { cause: Throwable ->
            cause.printStackTrace()
        }


    enum class CacheType {
        Network, Cache, LazyNetwork
    }
}