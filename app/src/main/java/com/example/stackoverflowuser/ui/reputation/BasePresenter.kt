package com.example.stackoverflowuser.ui.reputation

/**
 * Created by tho nguyen on 2019-05-11.
 */
interface BasePresenter<in T> {
    fun attach(view: T)

    fun detach()
}