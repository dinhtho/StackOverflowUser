package com.example.stackoverflowuser.ui.main

import com.example.stackoverflowuser.base.BasePresenter
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.User
import com.example.stackoverflowuser.services.stackoverflow_user.StackOverflowServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class MainActivityPresenter : BasePresenter<MainActivityView> {

    val TAG = "MainActivityPresenter"

    /**
     * The reference to [MainActivityView]
     * Created in [.attach] and destroyed in [.detach]
     */
    var mView: MainActivityView? = null

    override fun attach(view: MainActivityView) {
        this.mView = view
    }

    override fun detach() {
        this.mView = null
    }


    fun getUsers(page: Int) {
        mView?.showLoading()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val usersResponse =
                    StackOverflowServiceBuilder()
                        .getUsers(page, Constants.PAGE_SIZE, Constants.SITE).await()
                withContext(Dispatchers.Main) {
                    if (usersResponse.items != null && usersResponse.items is List<User>) {
                        mView?.updateUserAdapter(usersResponse.items!!)
                    }
                }
            } catch (throwable: Throwable) {
                withContext(Dispatchers.Main) {
                    mView?.onError(throwable)
                }
            } finally {
                withContext(Dispatchers.Main) {
                    mView?.hideLoading()
                }
            }

        }
    }

}
