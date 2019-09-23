package com.gmail.xuyimin1994.architecturecompentencedemo.utils

import java.util.regex.Pattern

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.utils
 *yida
 *2019/9/23 0023
 **/
class RegUtil {
    companion object{
        fun getAddress(input:String):String{
//            <meta name="description" content="https://3cc478c4.ngrok.io. Contribute to NByida/proxyAddress development by creating an account on GitHub.">
            var reg="<meta name=\"description\" content=\"(.*?). Contribute to"
            var pattern=Pattern.compile(reg)
            var matcher=pattern.matcher(input)
            if(matcher.find()){
                return matcher.group(1)+"/";
            }
            return "a"
        }
    }
}