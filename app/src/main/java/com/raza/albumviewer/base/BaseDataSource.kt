package com.raza.albumviewer.base

import retrofit2.Response
import timber.log.Timber

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): BaseResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return BaseResult.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    protected suspend fun <Any> getDefaultResult(call: suspend () -> Response<Any>): BaseResult<Any> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return BaseResult.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): BaseResult<T> {
        Timber.e(message)
        return BaseResult.error("Network call has failed for a following reason: $message")
    }

    private fun <T> success(message: String): BaseResult<T> {
        Timber.e(message)
        return BaseResult.error("Network call has failed for a following reason: $message")
    }

}