package com.example.stackoverflowuser.api

import com.example.stackoverflowuser.constants.Urls
import com.example.stackoverflowuser.network.NetworkProvider

object ApiBuilder {
    val stackOverflowAPI: StackOverflowAPI = NetworkProvider.instance.provideApi(Urls.BASE_URL)
}