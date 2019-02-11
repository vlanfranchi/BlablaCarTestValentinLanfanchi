package com.jehutyno.blablacartestvalentinlanfranchi

import android.app.Application
import com.jehutyno.api.API


class AndroidApp : Application() {

    val api by lazy { API() }

    override fun onCreate() {
        super.onCreate()

    }

}