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


    fun excuteMain(task:Runnable,delay:Long=0){
        handler.postDelayed(task,delay)
    }

    fun excuteMain(run:()->Unit,delay:Long=0){
        handler.postDelayed(object :Runnable{
            override fun run() {
                run()
            }
        },delay)
    }

    fun excuteIo(task:Runnable,delay:Long=0){
        excuteMain( Runnable {
            io_ThreadPool.execute(task)
        })
    }

    fun excuteCalculate(task:Runnable,delay:Long=0){
        excuteMain( Runnable {
            caculate_ThreadPool.execute(task)
        },delay)
    }


}