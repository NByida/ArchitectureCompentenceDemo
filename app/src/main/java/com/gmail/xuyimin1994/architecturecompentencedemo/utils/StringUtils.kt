package com.gmail.xuyimin1994.architecturecompentencedemo.utils

import android.text.Html
import android.text.Spanned

object StringUtils {

    fun getText(context:String): Spanned {
//        山外青山楼外楼，西湖歌舞几时休？<br/>暖风熏得游人醉，直把杭州作汴州。 <br>
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
//                .replace(",","\n")
//                .replace("。","\n")
//                .replace("；","\n")
//                .replace("？","\n")
//                .replace("！","\n")
        )
    }
}