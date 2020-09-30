package com.gmail.xuyimin1994.architecturecompentencedemo.ui.customeView

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.gmail.xuyimin1994.architecturecompentencedemo.app.App

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.ui.customeView
 *yida
 *2019/10/21 0021
 **/
class TypeFaceTextView(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    companion object {
        var typeFaceHold: Typeface? = null
        init {
            typeFaceHold = Typeface.createFromAsset(App.context.assets, "fonts/wyue.otf")
        }
    }

    init {
        typeface = typeFaceHold
    }
}