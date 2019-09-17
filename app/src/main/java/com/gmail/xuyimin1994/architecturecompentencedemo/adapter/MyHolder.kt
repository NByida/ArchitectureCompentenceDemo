package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import com.chad.library.adapter.base.BaseViewHolder
import android.graphics.Typeface



class MyHolder(view:View): BaseViewHolder(view){
    var context:Context

    init {
        this.context=view.context
    }

    fun setTextStyle(@IdRes viewId: Int): MyHolder {
        val view = getView<TextView>(viewId)
        val typeFaceHold = Typeface.createFromAsset(context.getAssets(), "fonts/xing_kai.ttf")
        view.setTypeface(typeFaceHold)
        return this
    }
}