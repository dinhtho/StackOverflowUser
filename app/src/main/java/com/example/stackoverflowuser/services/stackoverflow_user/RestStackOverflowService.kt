package com.example.stackoverflowuser.services.stackoverflow_user

import com.example.stackoverflowuser.model.ReputationsResponse
import com.example.stackoverflowuser.model.UsersResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by tho nguyen on 2019-05-11.
 */
interface RestStackOverflowService {
    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int,
        @Query("site") site: String
    ): Deferred<UsersResponse>

    @GET("users/{userId}/reputation-history")
    fun getUserReputations(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int,
        @Query("site") site: String
    ): Deferred<ReputationsResponse>
}