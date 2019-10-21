package com.gmail.xuyimin1994.architecturecompentencedemo.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context




/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.app
 *yida
 *2019/9/24 0024
 **/
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context=this
//        JAnalyticsInterface.init(this)
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }


}