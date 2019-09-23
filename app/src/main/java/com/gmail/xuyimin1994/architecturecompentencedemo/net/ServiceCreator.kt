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
    private const val BASE_URL = "http://3cc478c4.ngrok.io/"


    var httpLoggingInterceptor= HttpLoggingInterceptor()


    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
            //直接获取字符串
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    private val addressBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build())
            //直接获取字符串
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    private val retrofit = builder.build()
    private val retrofit4Address = addressBuilder.build()



    fun <T> create4Address(serviceClass: Class<T>): T {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return retrofit4Address.create(serviceClass)
    }


    fun <T> create(serviceClass: Class<T>): T {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return retrofit.create(serviceClass)
    }

}