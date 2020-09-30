package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.app.App
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ColorUtlis

class ColorAdapter : BaseQuickAdapter<String, MyHolder>(R.layout.item_color){
    override fun convert(helper: MyHolder?, item: String?) {
        helper?.getView<ImageView>(R.id.circle)?.setImageDrawable(ColorDrawable(ContextCompat.getColor(App.context,ColorUtlis.getIdByName(item!!.trim()))))
    }
}