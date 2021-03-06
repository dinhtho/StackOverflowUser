package com.example.stackoverflowuser.api

import com.example.stackoverflowuser.model.ReputationsResponse
import com.example.stackoverflowuser.model.UsersResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by tho nguyen on 2019-05-11.
 */
interface StackOverflowAPI {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int,
        @Query("site") site: String
    ): UsersResponse

    @GET("users/{userId}/reputation-history")
    suspend fun getUserReputations(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int,
        @Query("site") site: String
    ): ReputationsResponse
}