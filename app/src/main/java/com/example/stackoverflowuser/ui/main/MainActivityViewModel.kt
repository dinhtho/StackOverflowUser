package com.example.stackoverflowuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.User
import com.example.stackoverflowuser.network.Network
import com.example.stackoverflowuser.services.stackoverflow_user.StackOverflowServiceBuilder
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by tho nguyen on 2019-06-07.
 */

class MainActivityViewModel : ViewModel() {

    val users = MutableLiveData<MutableList<User>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Throwable>();

    fun getUsers(page: Int) {
        Network.request(
            scope = viewModelScope,
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
                    users.postValue(usersResponse.items)
                }
            },
            doOnTerminate = { loading.postValue(false) },
            doOnSubscribe = { loading.postValue(true) },
            error = { throwable -> error.postValue(throwable) }
        )
    }

    fun useMultipleCalls() {
        val x = StackOverflowServiceBuilder
            .getUsers(1, Constants.PAGE_SIZE, Constants.SITE)

        Network.multipleRequest(
            scope = viewModelScope,
            calls = listOf(x, x),
            success = { usersResponses ->
                Log.i(TAG, "getUsers: ${usersResponses?.size}")
            },
            doOnTerminate = { loading.postValue(false) },
            doOnSubscribe = { loading.postValue(true) },
            error = { throwable -> error.postValue(throwable) }
        )

    }


    private val TAG = "MainActivityViewModel"

    fun updateBookmark(user: User) {
        val realmDB = Realm.getDefaultInstance()
        realmDB.executeTransaction { realm ->
            if (user.isBookmarked) {
                realm.insert(user)
            } else {
                val userDB =
                    realm.where(User::class.java).equalTo("userId", user.userId).findFirst()
                userDB?.deleteFromRealm()
            }
        }
    }
}