package com.gmail.xuyimin1994.architecturecompentencedemo.net

import com.gmail.xuyimin1994.architecturecompentencedemo.app.App
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.net.api.AddressService
import com.gmail.xuyimin1994.architecturecompentencedemo.net.api.PoetryService
import io.reactivex.Observable
import okhttp3.Cache
import java.io.File

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.net
 *yida
 *2019/9/17 0017
 **/
class PoertyNet {
    private val poertyService = ServiceCreator.create(PoetryService::class.java)
    private val addressService = ServiceCreator.create4Address(AddressService::class.java)




    fun fetchAllPoetry(index: Int ): Observable<BaseBean> {
        return poertyService.getPoetry(index)
    }


    fun getNewAddress(): Observable<String> {
        return addressService.getAddress()
    }


    fun searchPoetry(name:String,index: Int ): Observable<BaseBean> {
        return poertyService.searchPoetry(name,index)
    }

    fun searchAll(name:String,index: Int ): Observable<BaseBean> {
        return poertyService.searchAll(name,index)
    }

    fun searchPoet(name:String,index: Int ): Observable<BaseBean> {
        return poertyService.searchPoet(name,index)
    }

    fun searchContent(name:String,index: Int ): Observable<BaseBean> {
        return poertyService.searchContext(name,index)
    }





    companion object{
        private var net:PoertyNet?=null

        fun getInstance():PoertyNet{
            if(net== null){
                synchronized(PoertyNet::class.java){
                    if(net== null){
                        net= PoertyNet()
                    }
                }
            }
            return net!!
        }
    }
}