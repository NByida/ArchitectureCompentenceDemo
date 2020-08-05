package com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi

import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout


/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.ui
 *yida
 *2019/10/11 0011
 **/
abstract class RvFragmnet: BaseFragment() {
    var page=1
    lateinit var refreshLayout: SmartRefreshLayout

    abstract fun pullData(page:Int)

    fun parentCreated(){
        initRefreshLayOut()
    }

    fun initRefreshLayOut(){
        refreshLayout=view!!.findViewById(R.id.refresh_layout)
        refreshLayout.setOnRefreshListener {
            page=1
                pullData(1)
            }
        refreshLayout.setOnLoadMoreListener{
            pullData(++page)}
    }
}