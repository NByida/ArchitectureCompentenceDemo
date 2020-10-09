package com.gmail.xuyimin1994.architecturecompentencedemo.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Typeface
import androidx.multidex.MultiDexApplication
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader

import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure


/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.app
 *yida
 *2019/9/24 0024
 **/
class App: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        context=this
//        UMConfigure.init(this, "5dadbaaf4ca357c215000020", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null)
//         选用AUTO页面采集模式
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
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