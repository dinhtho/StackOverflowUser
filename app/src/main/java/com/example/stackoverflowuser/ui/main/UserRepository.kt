package com.example.stackoverflowuser.ui.main

import com.example.stackoverflowuser.api.ApiBuilder

class UserRepository {
    fun getUsers(page: Int, pageSize: Int, site: String) = suspend {
        ApiBuilder.stackOverflowAPI.getUsers(page, pageSize, site)
    }
}