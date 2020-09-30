package com.gmail.xuyimin1994.architecturecompentencedemo.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object ThreadMangager {

    var io_ThreadPool = ThreadPoolExecutor(3, 30, 10, TimeUnit.MILLISECONDS,
            ArrayBlockingQueue(10))

    var caculate_ThreadPool = ThreadPoolExecutor(3, 30, 10,  TimeUnit.MILLISECONDS,
            ArrayBlockingQueue(10))

    var handler:Handler= Handler(Looper.getMainLooper())

    fun excuteMain(delay:Long=0,task:Runnable){
        handler.postDelayed(task,delay)
    }

    fun excuteMain(task:Runnable,delay:Long=0){
        handler.postDelayed(task,delay)
    }

    fun excuteIo(task:Runnable,delay:Long=0){
        excuteMain(delay, Runnable {
            io_ThreadPool.execute(task)
        })
    }

    fun excuteCalculate(task:Runnable,delay:Long=0){
        excuteMain(delay, Runnable {
            caculate_ThreadPool.execute(task)
        })
    }


}