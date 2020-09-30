package com.gmail.xuyimin1994.architecturecompentencedemo.utils

import com.gmail.xuyimin1994.architecturecompentencedemo.app.App

object ColorUtlis {

    fun getIdByName( resName: String): Int {
        return App.context.getResources().getIdentifier(resName, "color", App.context.getPackageName())
    }
}