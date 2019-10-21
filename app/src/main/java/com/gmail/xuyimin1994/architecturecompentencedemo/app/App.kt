package com.gmail.xuyimin1994.architecturecompentencedemo.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator






/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.app
 *yida
 *2019/9/24 0024
 **/
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context=this
//        JAnalyticsInterface.init(this)
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }


    init{
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.white, android.R.color.black)//全局设置主题颜色
            ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
}