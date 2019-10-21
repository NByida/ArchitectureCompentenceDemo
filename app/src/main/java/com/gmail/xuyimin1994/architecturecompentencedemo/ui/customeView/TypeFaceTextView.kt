package com.gmail.xuyimin1994.architecturecompentencedemo.ui.customeView

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.gmail.xuyimin1994.architecturecompentencedemo.R

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.ui.customeView
 *yida
 *2019/10/21 0021
 **/
class TypeFaceTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    init {
        val ta = context?.obtainStyledAttributes(attrs, R.styleable.TypeFaceTextView, 0, 0)
        var path:String?=ta?.getString( R.styleable.TypeFaceTextView_TypeFace_name)
        ta?.recycle()
        val typeFaceHold = Typeface.createFromAsset(context?.assets, if(path==null){
            "fonts/xing_kai.ttf"
        }else{
            "fonts/"+path+".ttf"
        })
//        typeface=typeFaceHold
    }
}