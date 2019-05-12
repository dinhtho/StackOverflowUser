package com.example.stackoverflowuser.ui.main

import com.example.stackoverflowuser.base.BasePresenter
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.User
import com.example.stackoverflowuser.model.UsersResponse
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

        CoroutineScope(Dispatchers.Main).launch {
            var usersResponse: UsersResponse? = null
            try {
                withContext(Dispatchers.IO) {
                    usersResponse = StackOverflowServiceBuilder()
                        .getUsers(page, Constants.PAGE_SIZE, Constants.SITE).await()
                }

                if (usersResponse?.items != null && usersResponse?.items is MutableList<User>) {
                    mView?.updateUserAdapter(usersResponse?.items!!)

                }
            } catch (throwable: Throwable) {
                mView?.onError(throwable)

            } finally {
                mView?.hideLoading()
            }

        }
    }

}
