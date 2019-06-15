package com.example.stackoverflowuser.network

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
        scope.launch {
            doOnSubscribe?.invoke()
            try {
                success?.invoke(call.invoke())
            } catch (t: Throwable) {
                error?.invoke(t)
            } finally {
                doOnTerminate?.invoke()
            }
        }
    }
}