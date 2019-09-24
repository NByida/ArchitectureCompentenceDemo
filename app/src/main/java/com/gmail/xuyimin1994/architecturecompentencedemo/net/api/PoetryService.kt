package com.gmail.xuyimin1994.architecturecompentencedemo.net.api

import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.net.api
 *yida
 *2019/9/17 0017
 **/
interface PoetryService {
    @GET("all/{index}")
    fun getPoetry(@Path("index") index: Int): Observable<BaseBean>



    @GET("name/{name}/{index}")
    fun searchPoetry(@Path("name") name: String,@Path("index") index: Int): Observable<BaseBean>

}