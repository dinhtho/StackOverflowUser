package com.example.stackoverflowuser.services.stackoverflow_user

import com.example.stackoverflowuser.constants.Urls
import com.example.stackoverflowuser.model.ReputationsResponse
import com.example.stackoverflowuser.model.UsersResponse
import com.example.stackoverflowuser.network.NetworkProvider
import kotlinx.coroutines.Deferred


class StackOverflowServiceBuilder {

    companion object {
        fun getUsers(page: Int, pageSize: Int, site: String): Deferred<UsersResponse> {
            val restStackOverflowUserService = NetworkProvider.instance
                .provideApi(Urls.BASE_URL, RestStackOverflowService::class.java)
            return restStackOverflowUserService.getUsers(page, pageSize, site)
        }

        fun getUserReputations(userId: String, page: Int, pageSize: Int, site: String): Deferred<ReputationsResponse> {
            val restStackOverflowUserService = NetworkProvider.instance
                .provideApi(Urls.BASE_URL, RestStackOverflowService::class.java)
            return restStackOverflowUserService.getUserReputations(userId, page, pageSize, site)
        }
    }
}