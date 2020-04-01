package com.example.stackoverflowuser.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.stackoverflowuser.base.viewmodel.BaseViewModel
import com.example.stackoverflowuser.constants.Constants
import com.example.stackoverflowuser.model.UsersResponse

/**
 * Created by tho nguyen on 2019-06-07.
 */

class MainActivityViewModel : BaseViewModel() {
    private val users = MutableLiveData<UsersResponse>()
    private val userRepository = UserRepository()

    fun users() = users

    fun getUsers(page: Int) {
        executeRoot(userRepository.getUsers(page, Constants.PAGE_SIZE, Constants.SITE), users)
    }


//    val users = MutableLiveData<MutableList<User>>()
//    val loading = MutableLiveData<Boolean>()
//    val error = MutableLiveData<Throwable>();
//
//    fun getUsers(page: Int) {
//        Network.request(
//            scope = viewModelScope,
//            call = StackOverflowServiceBuilder
//                .getUsers(page, Constants.PAGE_SIZE, Constants.SITE),
//            success = { usersResponse ->
//                if (usersResponse?.items != null && usersResponse.items is MutableList<User>) {
//                    val realm = Realm.getDefaultInstance()
//                    val usersDB = realm.where(User::class.java).findAll()
//                    for (i in usersDB) {
//                        for (user in usersResponse.items!!) {
//                            if (user.userId == i.userId) {
//                                user.isBookmarked = i.isBookmarked
//                                break
//                            }
//                        }
//                    }
//                    users.postValue(usersResponse.items)
//                }
//            },
//            doOnTerminate = { loading.postValue(false) },
//            doOnSubscribe = { loading.postValue(true) },
//            error = { throwable -> error.postValue(throwable) }
//        )
//    }
//
//    fun useMultipleCalls() {
//        val x = StackOverflowServiceBuilder
//            .getUsers(1, Constants.PAGE_SIZE, Constants.SITE)
//
//        Network.multipleRequest(
//            scope = viewModelScope,
//            calls = listOf(x, x),
//            success = { usersResponses ->
//                Log.i(TAG, "getUsers: ${usersResponses?.size}")
//            },
//            doOnTerminate = { loading.postValue(false) },
//            doOnSubscribe = { loading.postValue(true) },
//            error = { throwable -> error.postValue(throwable) }
//        )
//
//    }
//
//
//    private val TAG = "MainActivityViewModel"
//
//    fun updateBookmark(user: User) {
//        val realmDB = Realm.getDefaultInstance()
//        realmDB.executeTransaction { realm ->
//            if (user.isBookmarked) {
//                realm.insert(user)
//            } else {
//                val userDB =
//                    realm.where(User::class.java).equalTo("userId", user.userId).findFirst()
//                userDB?.deleteFromRealm()
//            }
//        }
//    }
}