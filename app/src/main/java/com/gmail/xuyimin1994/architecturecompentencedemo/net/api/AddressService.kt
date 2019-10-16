package com.gmail.xuyimin1994.architecturecompentencedemo.net.api

import retrofit2.Call
import retrofit2.http.GET

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.net.api
 *yida
 *2019/9/23 0023
 **/
interface AddressService {
    @GET("https://github.com/NByida/proxyAddress/blob/master/address")
    fun getAddress(): Call<String>
}