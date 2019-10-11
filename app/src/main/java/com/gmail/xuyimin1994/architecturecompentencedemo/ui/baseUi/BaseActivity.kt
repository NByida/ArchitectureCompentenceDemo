package com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.gmail.xuyimin1994.architecturecompentencedemo.event.Default
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi
 *yida
 *2019/10/11 0011
 **/
open abstract class BaseActivity: AppCompatActivity() {

    abstract fun getLayoutId():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(getLayoutId())
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun default(event: Default) {

    }
}