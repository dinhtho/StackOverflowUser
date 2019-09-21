package com.example.stackoverflowuser.application

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker






/**
 * Created by tho nguyen on 2019-05-12.
 */
class MyApplication: Application() {
    private var sAnalytics: GoogleAnalytics? = null
    private var sTracker: Tracker? = null
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)

        sAnalytics = GoogleAnalytics.getInstance(this)
    }

    @Synchronized
    fun getDefaultTracker(): Tracker {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics!!.newTracker("UA-148412674-2")
        }

        return sTracker!!
    }
}