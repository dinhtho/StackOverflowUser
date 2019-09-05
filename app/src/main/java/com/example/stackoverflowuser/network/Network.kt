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


    fun <T> multipleRequest(
        scope: CoroutineScope = CoroutineScope(Dispatchers.Main),
        calls: List<suspend () -> T>,
        success: ((responses: List<T>?) -> Unit)?,
        error: ((throwable: Throwable) -> Unit)? = null,
        doOnSubscribe: (() -> Unit)? = null,
        doOnTerminate: (() -> Unit)? = null
    ) {
        scope.launch {
            doOnSubscribe?.invoke()
            try {
                val requests: MutableList<Deferred<T>> = mutableListOf()
                val results: MutableList<T> = mutableListOf()
                calls.forEach {
                    val data = async {
                        it.invoke()
                    }
                    requests.add(data)
                }
                requests.forEach {
                    results.add(it.await())
                }
                success?.invoke(results)
            } catch (t: Throwable) {
                error?.invoke(t)
            } finally {
                doOnTerminate?.invoke()
            }
        }
    }
}