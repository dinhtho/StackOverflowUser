package com.example.stackoverflowuser.application

import android.app.Application
import com.example.stackoverflowuser.base.repository.isNetworkStatusAvailable
import com.example.stackoverflowuser.util.value
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Created by tho nguyen on 2019-05-12.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)

        instance = this

    }

    companion object {
        private var instance: MyApplication? = null
        fun isConnectInternet() = this.instance?.isNetworkStatusAvailable().value()
    }


}