package com.gmail.xuyimin1994.architecturecompentencedemo.net.api

import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.net.api
 *yida
 *2019/9/17 0017
 **/
interface PoetryService  {
    @GET("all/{index}")
//    @Headers("Cache-control:max-stale="+36000)
    fun getPoetry(@Path("index") index: Int): Observable<BaseBean>



    @GET("name/{name}/{index}")
//    @Headers("Cache-control:max-stale="+36000)
    fun searchPoetry(@Path("name") name: String,@Path("index") index: Int): Observable<BaseBean>

    @GET("all/{name}/{index}")
//    @Headers("Cache-control:max-stale="+36000)
    fun searchAll(@Path("name") name: String,@Path("index") index: Int): Observable<BaseBean>

    @GET("auth/{name}/{index}")
//    @Headers("Cache-control:max-stale="+36000)
    fun searchPoet(@Path("name") name: String,@Path("index") index: Int): Observable<BaseBean>

    @GET("content/{name}/{index}")
//    @Headers("Cache-control:max-stale="+36000)
    fun searchContext(@Path("name") name: String,@Path("index") index: Int): Observable<BaseBean>

}