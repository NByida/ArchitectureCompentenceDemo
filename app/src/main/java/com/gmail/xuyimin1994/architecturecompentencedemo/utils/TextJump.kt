package com.gmail.xuyimin1994.architecturecompentencedemo.utils

import android.text.Spanned
import android.widget.TextView

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.utils
 *yida
 *2019/9/26 0026
 **/
class TextJump {
//    private static TextView tv;
//    private static String s;
//    private static int length;
//    private static long time;
//    static int n = 0;
//    private static int nn;
    companion object{
        lateinit var tv:TextView
        lateinit var s: Spanned
        var length:Int=0
        var time:Long=0
        var n:Int=0
        var nn:Int=0

    fun init( tv:TextView,  s:Spanned,  time:Long){
        this.tv=tv
        this.s=s
        this.time=time*1000
        this.length = s.length;
        startTv(n)
    }
        fun startTv(n:Int){
            Thread{
                try {
                    var stv:String = s.substring(0, n);//截取要填充的字符串
                    tv.post({
                            tv.setText(stv);

                    });
                    Thread.sleep(time/ length);//休息片刻
                    nn = n + 1;//n+1；多截取一个
                    if (nn <= length) {//如果还有汉字，那么继续开启线程，相当于递归的感觉
                        startTv(nn);
                    }
                } catch ( e:InterruptedException) {
                    e.printStackTrace();
                }
            }.start()
        }

    }

}