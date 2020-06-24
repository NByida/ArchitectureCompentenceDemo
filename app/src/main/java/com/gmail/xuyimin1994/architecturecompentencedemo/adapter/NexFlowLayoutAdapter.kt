package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import android.content.Context
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.TextView
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.nex3z.flowlayout.FlowLayout
import com.pdog.dimension.dp
import java.util.*

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.adapter
 *yida
 *2019/11/13 0013
 **/
class NexFlowLayoutAdapter {
    protected var flowLayout: FlowLayout
    protected var context: Context
    protected var Reverse = false
    protected var arraylist: List<String>? = null
    protected var maxArrayLength = 0
    protected var maxLength = 0

    protected var ItemClick: FlowItemClick? = null

    constructor(flowLayout: FlowLayout, arrayList: List<*>?, needReverse: Boolean) {
        this.flowLayout = flowLayout
        this.Reverse = needReverse
        if (needReverse && arrayList != null) {
            Collections.reverse(arrayList)
        }
        this.arraylist = arrayList as List<String>?
        this.context = flowLayout.context
        init(arrayList)
    }

    constructor(flowLayout: FlowLayout, arrayList: List<*>?, needReverse: Boolean, maxLength: Int, maxArrayLength: Int) {
        this.maxLength = maxLength
        this.maxArrayLength = maxArrayLength
        this.flowLayout = flowLayout
        this.Reverse = needReverse
        if (needReverse && arrayList != null) {
            Collections.reverse(arrayList)
        }
        this.arraylist = arrayList as List<String>?
        this.context = flowLayout.context
        init(arrayList)
    }

    fun setNeedReverse(needReverse: Boolean) {
        this.Reverse = needReverse
    }

    fun getArrayList(): List<*>? {
        return arraylist
    }

    fun setArrayList(arrayList: ArrayList<String>?) {
        if (arrayList == null) return
        if (Reverse) {
            Collections.reverse(arrayList)
        }
        this.arraylist = arrayList
        flowLayout.removeAllViews()
        init(arrayList)
    }

    protected fun init(arrayList: List<*>?) {
        var arrayList = arrayList
        if (arrayList == null || arrayList.size == 0) return
        if (maxArrayLength != 0 && maxArrayLength < arrayList.size) {
            arrayList = arrayList.subList(0, maxArrayLength)
        }
        val iterator = arrayList.iterator()
        flowLayout.removeAllViews()
        while (iterator.hasNext()) {
            val textView = TextView(flowLayout.context)
            textView.textSize = 12f
            textView.maxLines = 1
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textView.layoutParams = layoutParams
            textView.ellipsize = TextUtils.TruncateAt.END
            val text:String = iterator.next() as String

            if (maxLength != 0 && text.length > maxLength) {
                textView.maxEms = maxLength
            }
            textView.text= text as CharSequence?

            val padLeft =12.dp
            val padRight = 6.dp
            textView.setPadding(padLeft, padRight, padLeft, padRight)
            textView.setTextColor(context.resources.getColor(R.color.white))
            textView.background = context.resources.getDrawable(R.drawable.bg_round_corner_f7f7f7_15)
            textView.setOnClickListener { v ->
                if (ItemClick != null) {
                   ItemClick!!.onclick(textView.text.toString())
                }
            }
            flowLayout.addView(textView)
        }
    }

    fun setFlowItemClick(flowItemClick: FlowItemClick) {
        this.ItemClick = flowItemClick
    }


    interface PositionClick {
        fun click(text: String, position: Int)
    }
}

interface FlowItemClick {
    fun onclick(toString: String)
}
