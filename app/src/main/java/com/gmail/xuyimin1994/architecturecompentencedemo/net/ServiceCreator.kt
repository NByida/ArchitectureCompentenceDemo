package com.gmail.xuyimin1994.architecturecompentencedemo.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.net
 *yida
 *2019/7/1 0001
 **/
object ServiceCreator {
    private const val BASE_URL = "http://192.168.31.67/"


    var httpLoggingInterceptor= HttpLoggingInterceptor()


    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
            //直接获取字符串
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return retrofit.create(serviceClass)
    }

}