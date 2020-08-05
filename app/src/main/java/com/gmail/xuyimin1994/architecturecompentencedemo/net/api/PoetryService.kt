package com.gmail.xuyimin1994.architecturecompentencedemo.net.api

import com.gmail.xuyimin1994.architecturecompentencedemo.entity.*

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

    @GET("tag/{index}")
    fun getTags(@Path("index") index: Int): Call<TagWrap>


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

    @GET("tag/{tagName}/{index}")
    fun getPoetryUnderTag(@Path("tagName") tagName:String,@Path("index") index:Int): Call<BaseBean>

    /**
     * 获取所有名句
     */
    @GET("/word/{index}")
    fun getMingju(@Path("index") index:Int): Call<WordWrap>


    /**
     * 获取所有大标签
     */
    @GET("/label/{index}")
    fun getLabel(@Path("index") index:Int): Call<LabelWrap>


    /**
     * 获取所有小标签
     */
    @GET("/label/{labelId}/{index}")
    fun getTypes(@Path("labelId") labelId:String,@Path("index") index:Int): Call<Wrap<Type>>

    /**
     * 小标签下的诗句
     */
    @GET("/typeName/{typeName}/{index}")
    fun getWordByType(@Path("typeName") typeName:String,@Path("index") index:Int): Call<WordWrap>


}