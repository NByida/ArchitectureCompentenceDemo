package com.gmail.xuyimin1994.architecturecompentencedemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.databinding.DataBindingUtil
import com.gmail.xuyimin1994.architecturecompentencedemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding:ActivityMainBinding   = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val user = User("aaa")
        binding.setUser(user)
        user.change()
    }
}
