package com.example.stackoverflowuser.ui.reputation

import com.example.stackoverflowuser.base.BasePresenter
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.Reputation
import com.example.stackoverflowuser.model.ReputationsResponse
import com.example.stackoverflowuser.model.User
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
        mView?.showLoading()

        CoroutineScope(Dispatchers.Main).launch {
            var reputationsResponse: ReputationsResponse? = null
            try {
                withContext(Dispatchers.IO) {
                    reputationsResponse = StackOverflowServiceBuilder
                        .getUserReputations(userId,page, Constants.PAGE_SIZE, Constants.SITE).await()
                }
                if (reputationsResponse?.items != null && reputationsResponse?.items is MutableList<Reputation>) {
                    mView?.updateReputationAdapter(reputationsResponse?.items!!)
                }

            } catch (throwable: Throwable) {
                mView?.onError(throwable)

            } finally {
                mView?.hideLoading()
            }

        }
    }
}
