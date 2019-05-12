package com.example.stackoverflowuser.application

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by tho nguyen on 2019-05-12.
 */
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }
}