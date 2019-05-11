package com.example.stackoverflowuser.ui.main

import com.example.stackoverflowuser.model.User


interface MainActivityView {

    fun showLoading()

    fun hideLoading()

    fun updateUserAdapter(users: List<User>)

    fun onError(throwable: Throwable)
}

