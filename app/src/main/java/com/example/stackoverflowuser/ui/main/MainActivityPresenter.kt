package com.example.stackoverflowuser.ui.main

import com.example.stackoverflowuser.base.BasePresenter
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.User
import com.example.stackoverflowuser.model.UsersResponse
import com.example.stackoverflowuser.network.Network
import com.example.stackoverflowuser.services.stackoverflow_user.StackOverflowServiceBuilder
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
        Network.request(
            call = StackOverflowServiceBuilder
                .getUsers(page, Constants.PAGE_SIZE, Constants.SITE),
            success = { usersResponse ->
                if (usersResponse?.items != null && usersResponse.items is MutableList<User>) {
                    val realm = Realm.getDefaultInstance()
                    val usersDB = realm.where(User::class.java).findAll()
                    for (i in usersDB) {
                        for (user in usersResponse.items!!) {
                            if (user.userId == i.userId) {
                                user.isBookmarked = i.isBookmarked
                                break
                            }
                        }
                    }
                    mView?.updateUserAdapter(usersResponse.items!!)
                }
            },
            doOnTerminate = { mView?.hideLoading() },
            doOnSubscribe = { mView?.showLoading() },
            error = { throwable -> mView?.onError(throwable) }
        )
    }

    fun updateBookmark(user: User) {
        val realmDB = Realm.getDefaultInstance()
        realmDB.executeTransaction { realm ->
            if (user.isBookmarked) {
                realm.insert(user)
            } else {
                val userDB = realm.where(User::class.java).equalTo("userId", user.userId).findFirst()
                userDB?.deleteFromRealm()
            }
        }
    }
}
