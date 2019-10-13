package com.elifox.legocatalog.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.raza.albumviewer.base.BaseResult
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.Status.SUCCESS] - with data from database
 * [Result.Status.ERROR] - if error has occurred from any source
 * [Result.Status.LOADING]
 */
fun <T, A> resultLiveData(
    networkOnly: Boolean = false,
    databaseOnly: Boolean = false,
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> BaseResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<BaseResult<T>> =
    liveData(Dispatchers.IO) {
        emit(BaseResult.loading<T>())
        val source = databaseQuery.invoke().map { BaseResult.success(it) }
        if (databaseOnly) {
            emitSource(source)
        } else {
            val responseStatus = networkCall.invoke()
            if (responseStatus.status == BaseResult.Status.SUCCESS) {
                val source = databaseQuery.invoke().map { BaseResult.success(it) }
                emitSource(source)
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == BaseResult.Status.ERROR) {
                emit(BaseResult.error<T>(responseStatus.message!!))
                emitSource(source)
            }
        }
    }

/**
 * Kotlin extensions for dependency injection
 */

inline fun <reified T : ViewModel> FragmentActivity.injectViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.injectViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}