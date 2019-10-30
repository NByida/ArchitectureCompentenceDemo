package com.gmail.xuyimin1994.architecturecompentencedemo.net.api

import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean

import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Path

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.net.api
 *yida
 *2019/9/17 0017
 **/
interface PoetryService  {
    @GET("all/{index}")
    fun getPoetry(@Path("index") index: Int): Call<BaseBean>


    @GET("name/{name}/{index}")
    fun searchPoetry(@Path("name") name: String,@Path("index") index: Int): Call<BaseBean>

    @GET("all/{name}/{index}")
    fun searchAll(@Path("name") name: String,@Path("index") index: Int): Call<BaseBean>

    @GET("auth/{name}/{index}")
    fun searchPoet(@Path("name") name: String,@Path("index") index: Int): Call<BaseBean>

    @GET("content/{name}/{index}")
    fun searchContext(@Path("name") name: String,@Path("index") index: Int): Call<BaseBean>

    @GET("recommend/{id}/{index}")
    fun getRecommendList(@Path("id") id: String,@Path("index") index: Int): Call<BaseBean>

}