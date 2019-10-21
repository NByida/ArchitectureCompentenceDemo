package com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi

import androidx.appcompat.app.AppCompatActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.ui
 *yida
 *2019/9/24 0024
 **/
abstract class RvActivity: BaseActivity() {
    var page=1
    lateinit var refreshLayout:SmartRefreshLayout

    abstract fun pullData(page:Int)

    fun parentCreated(){
        initRefreshLayOut()
    }

    fun initRefreshLayOut(){
        refreshLayout=findViewById(R.id.refresh_layout)
        refreshLayout?.let{
            it.setOnRefreshListener {
                page=1
                pullData(1)
            }
            it.setOnLoadMoreListener{
                pullData(++page)}
        }
    }

}