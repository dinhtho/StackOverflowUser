package com.example.stackoverflowuser.base

/**
 * Created by tho nguyen on 2019-05-11.
 */
interface BasePresenter<in T> {
    fun attach(view: T)

    fun detach()
}