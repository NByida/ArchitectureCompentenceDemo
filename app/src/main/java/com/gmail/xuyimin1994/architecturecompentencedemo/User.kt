package com.gmail.xuyimin1994.architecturecompentencedemo

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


class User(name:String) : ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData<String>()


    var pic:String="https://i0.hdslb.com/bfs/live/room_cover/8df838340ec6c09b42e915a0d9eddbede897e3ad.jpg@320w_200h.webp"

    fun change(){
        var time:Long=0
        var runnable= Runnable {
            while (true){
                if(System.currentTimeMillis()-time>1000){
                    name.value="1111111111"
                    time=System.currentTimeMillis()
                }
            }
        }
        runnable.run()
    }

}