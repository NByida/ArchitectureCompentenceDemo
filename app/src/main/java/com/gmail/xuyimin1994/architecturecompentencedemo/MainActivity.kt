package com.gmail.xuyimin1994.architecturecompentencedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.databinding.DataBindingUtil
import android.util.Log

import com.gmail.xuyimin1994.architecturecompentencedemo.databinding.ActivityMainBinding
import rx.Observable
import rx.android.schedulers.AndroidSchedulers

import java.util.concurrent.TimeUnit
import android.R.attr.name
import android.databinding.ObservableField




class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding:ActivityMainBinding   = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val user = User(ObservableField("111"),ObservableField("https://i0.hdslb.com/bfs/live/room_cover/8df838340ec6c09b42e915a0d9eddbede897e3ad.jpg@320w_200h.webp"))
        binding.user=user
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {_->kotlin.run {
                    user.name.set(System.currentTimeMillis().toString())
                }}
    }
}
