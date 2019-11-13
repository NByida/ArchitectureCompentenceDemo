package com.gmail.xuyimin1994.architecturecompentencedemo.net

import com.gmail.xuyimin1994.architecturecompentencedemo.app.App
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.net.api.AddressService
import com.gmail.xuyimin1994.architecturecompentencedemo.net.api.PoetryService
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.net
 *yida
 *2019/9/17 0017
 **/
class PoertyNet {
    private val poertyService = ServiceCreator.create(PoetryService::class.java)

    suspend fun fetchAllPoetry(index: Int ) = withContext(Dispatchers.IO){
        var result:BaseBean=poertyService.getPoetry(index).await()
        result
    }

    suspend fun searchPoetry(name:String,index: Int )= withContext(Dispatchers.IO){
        var result:BaseBean=poertyService.searchPoetry(name,index).await()
        result
    }

    suspend fun searchAll(name:String,index: Int )=withContext(Dispatchers.IO){
        var result:BaseBean= poertyService.searchAll(name,index).await()
        result
    }

    suspend fun searchPoet(name:String,index: Int )= withContext(Dispatchers.IO) {
        var result:BaseBean=poertyService.searchPoet(name, index).await()
        result
    }

    suspend fun searchContent(name:String,index: Int )= withContext(Dispatchers.IO){
        var result:BaseBean=poertyService.searchContext(name,index).await()
        result
    }

    suspend fun getRecommend(poetryId:String,page:Int)= withContext(Dispatchers.IO){
        var result:BaseBean=poertyService.getRecommendList(poetryId,page).await()
        result
    }

    suspend fun getPoetryUnderTag(tagName:String,page:Int)= withContext(Dispatchers.IO){
        var result:BaseBean= poertyService.getPoetryUnderTag(tagName,page).await()
        result
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

private suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resumeWithException(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                if (body != null) continuation.resume(body)
                else continuation.resumeWithException(RuntimeException("response body is null"))
            }
        })
    }
}
