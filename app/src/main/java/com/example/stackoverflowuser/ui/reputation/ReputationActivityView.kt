package com.example.stackoverflowuser.ui.reputation

import com.example.stackoverflowuser.model.Reputation
import com.example.stackoverflowuser.model.User


interface ReputationActivityView {

    fun showLoading()

    fun hideLoading()

    fun updateReputationAdapter(reputations: MutableList<Reputation>)

    fun onError(throwable: Throwable)
}

