package com.gmail.xuyimin1994.architecturecompentencedemo

import android.os.Bundle
import android.util.Log

import com.gmail.xuyimin1994.architecturecompentencedemo.databinding.ActivityMainBinding

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProviders
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val viewModel = ViewModelProviders.of(this)).get(PoetryViewModel::class.java)


        var binding:ActivityMainBinding   = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val user = User(ObservableField("111"),ObservableField("https://i0.hdslb.com/bfs/live/room_cover/8df838340ec6c09b42e915a0d9eddbede897e3ad.jpg@320w_200h.webp"))
        binding.user=user
        PoertyNet.getInstance().fetchAllPoetry(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> user.name.set(response.list?.get(0)?.content)},{t->Log.e("onFailure", t.message)})


    }
}
