package com.gmail.xuyimin1994.architecturecompentencedemo.utils

import android.text.Html
import android.text.Spanned

object StringUtils {

    fun getText(context:String): Spanned {

        return Html.fromHtml(context.replace("</p> <p>","<br>")!!
                .replace("，<br>","，")
                .replace("，","，<br>")
                .replace("；","；<br>")
                .replace("？","，<br>")
                .replace("！","，<br>")
                .replace("。&nbsp","。")
                .replace("。&nbsp;","。")
                .replace("。&nbsp ;","。")
                .replace("。 <br/>","。")
                .replace("。 <br>","。")
                .replace("。<br/>","。")
                .replace("。<br>","。")
                .replace("。","。<br>")
                .replace("。<br>;","。<br>")
                .replace("<br> <br>","<br>")
                .replace("<br><br/>","<br>")
                .replace("<br><br>","<br>")
                .replace("<br></span><br/>","<br>")
                .replace(",","\n")
                .replace("。","\n")
                .replace("；","\n")
                .replace("？","\n")
                .replace("！","\n")
        )
    }
}