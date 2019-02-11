package com.jehutyno.blablacartestvalentinlanfranchi

import android.app.Application
import com.jehutyno.api.BlablaCarApi


class AndroidApp : Application() {

    val api by lazy { BlablaCarApi() }

    override fun onCreate() {
        super.onCreate()

    }

}