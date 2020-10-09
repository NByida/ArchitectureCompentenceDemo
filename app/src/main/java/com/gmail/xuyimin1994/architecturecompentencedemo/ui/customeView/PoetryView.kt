package com.gmail.xuyimin1994.architecturecompentencedemo.ui.customeView

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginLeft
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.DisplayUtils
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.StringUtils
import kotlinx.android.synthetic.main.activity_mingju.view.*


class PoetryView@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {
    fun setStringArray(array:List<String>){
        array.reversed().forEach {
            var text=TypeFaceTextView(context,null)
            text.maxEms=1
            text.textSize=18f
            text.text=StringUtils.getText(it).toString().trim()
            addView(text)
            text.post {
                val params=text.layoutParams as? LayoutParams
                params?.let {
                    params.leftMargin=DisplayUtils.dip2px(context,3f)
                    text.layoutParams=params
                }
            }
        }
    }

    fun setColor(color:Int){
        for (i in 0 until childCount){
           if(getChildAt(i) is TextView){
               ( getChildAt(i) as? TextView)?.setTextColor(color)
           }
        }
    }
}

