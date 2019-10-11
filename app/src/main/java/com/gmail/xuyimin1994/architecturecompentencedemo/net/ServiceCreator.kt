package com.gmail.xuyimin1994.architecturecompentencedemo.net

import com.gmail.xuyimin1994.architecturecompentencedemo.app.App
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.SharedPreferenceUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.net
 *yida
 *2019/7/1 0001
 **/
object ServiceCreator {

//    private const val BASE_URL = "http://34.92.180.73"
    private const val BASE_URL = "http://192.168.1.75"


    var httpLoggingInterceptor= HttpLoggingInterceptor()

    internal var cacheFile = File(App.context.getCacheDir().getAbsolutePath(), "HttpCache")
    internal var cache = Cache(cacheFile, (1024 * 1024 * 10).toLong())//缓存文件为10MB

    var regainAddress=false

    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder()
//                    .cache(cache)
                    .addInterceptor(httpLoggingInterceptor).build())
            //直接获取字符串
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    fun getAddress():String{
        var address=SharedPreferenceUtil.getInstance().get(App.context,"address","a") as String
            return (if(address.equals("a")) BASE_URL else address)
    }

    private val addressBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().cache(cache).addInterceptor(httpLoggingInterceptor).build())
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