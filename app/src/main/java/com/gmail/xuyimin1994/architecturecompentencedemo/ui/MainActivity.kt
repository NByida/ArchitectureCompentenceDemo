package com.gmail.xuyimin1994.architecturecompentencedemo.ui


import android.os.Bundle
import android.util.Log

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.PoetryAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.SharedPreferenceUtil
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.PoetryViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : RvActivity() {


    lateinit var adapter:PoetryAdapter
    lateinit var viewModel:PoetryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(PoetryViewModel::class.java)
        parentCreated()
        initRv()
    }

    fun initRv(){
        var manager= LinearLayoutManager(this)
        rv_auto.layoutManager=manager
        adapter= PoetryAdapter()
        adapter.bindToRecyclerView(rv_auto)
        adapter.onItemClickListener= BaseQuickAdapter.OnItemClickListener { a, v, p ->PoetryDetailActivity.startMe(this@MainActivity, a.getItem(p) as Poetry) }
    }

    override fun pullData(page: Int) {
        getWeather(page,viewModel)
        if (page==1){
            getAddress(viewModel)
        }
    }

    fun getWeather(page:Int,viewModel:PoetryViewModel){
        viewModel.getWeather(page).observe(this, Observer {list->
            if(page==1){
                refreshLayout.finishRefresh()
                adapter.replaceData(list)
            }else{
                refreshLayout.finishLoadMore()
                adapter.addData(list)
            }
            adapter.notifyDataSetChanged()
        })
    }

    fun getAddress(viewModel:PoetryViewModel){
        viewModel.getAddress().observe(this, Observer<String>{s->
            SharedPreferenceUtil.getInstance().put(this,"address",s)})
    }
}
