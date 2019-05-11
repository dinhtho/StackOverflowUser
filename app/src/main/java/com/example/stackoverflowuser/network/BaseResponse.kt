package com.example.stackoverflowuser.network

/**
 * Created by tho nguyen on 2019-05-11.
 */
interface BaseResponse<T> {
    var data: T

    var throwable: Throwable

    var isSuccessful: Boolean
}