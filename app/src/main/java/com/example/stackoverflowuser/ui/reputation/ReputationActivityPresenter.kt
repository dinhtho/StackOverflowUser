package com.example.stackoverflowuser.ui.reputation

import com.example.stackoverflowuser.base.BasePresenter
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.Reputation
import com.example.stackoverflowuser.model.ReputationsResponse
import com.example.stackoverflowuser.model.User
import com.example.stackoverflowuser.network.Network
import com.example.stackoverflowuser.services.stackoverflow_user.StackOverflowServiceBuilder
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ReputationActivityPresenter : BasePresenter<ReputationActivityView> {

    var mView: ReputationActivityView? = null

    override fun attach(view: ReputationActivityView) {
        this.mView = view
    }

    override fun detach() {
        this.mView = null
    }


    fun getUserReputations(userId: String, page: Int) {
        Network.request(
            doOnSubscribe = { mView?.showLoading() },
            doOnTerminate = { mView?.hideLoading() },
            error = { throwable -> mView?.onError(throwable) },
            call = StackOverflowServiceBuilder
                .getUserReputations(userId, page, Constants.PAGE_SIZE, Constants.SITE),
            success = { reputationsResponse ->
                if (reputationsResponse?.items != null && reputationsResponse.items is MutableList<Reputation>) {
                    mView?.updateReputationAdapter(reputationsResponse.items!!)
                }
            }
        )
    }
}
