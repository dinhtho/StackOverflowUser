package com.example.stackoverflowuser.network

import android.util.Log
import kotlinx.coroutines.*

/**
 * Created by tho nguyen on 2019-05-15.
 */
object Network {
    fun <T> request(
        scope: CoroutineScope = CoroutineScope(Dispatchers.Main),
        call: suspend () -> T,
        success: ((response: T?) -> Unit)?,
        error: ((throwable: Throwable) -> Unit)? = null,
        doOnSubscribe: (() -> Unit)? = null,
        doOnTerminate: (() -> Unit)? = null
    ) {
        doOnSubscribe?.invoke()
        val IOContext = Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            error?.invoke(throwable)
            doOnTerminate?.invoke()
        }
        scope.launch(IOContext) {
            success?.invoke(call.invoke())
            doOnTerminate?.invoke()
        }
    }

    fun <T> multipleRequest(
        scope: CoroutineScope = CoroutineScope(Dispatchers.Main),
        calls: List<suspend () -> T>,
        success: ((responses: List<T>?) -> Unit)?,
        error: ((throwable: Throwable) -> Unit)? = null,
        doOnSubscribe: (() -> Unit)? = null,
        doOnTerminate: (() -> Unit)? = null
    ) {
        doOnSubscribe?.invoke()
        val IOContext = Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            error?.invoke(throwable)
            doOnTerminate?.invoke()
        }
        scope.launch(IOContext) {
            val results = calls.map { async(IOContext) { it.invoke() } }.map { it.await() }.toList()
            success?.invoke(results)
            doOnTerminate?.invoke()
        }
    }
}