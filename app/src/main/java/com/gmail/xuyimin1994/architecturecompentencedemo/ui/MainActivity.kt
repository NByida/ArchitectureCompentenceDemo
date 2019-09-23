package com.gmail.xuyimin1994.architecturecompentencedemo.ui


import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.PoetryAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.PoetryViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout


class MainActivity : AppCompatActivity() {
    var page=1;
    lateinit var adapter:PoetryAdapter;
    lateinit var refreshLayout:SmartRefreshLayout;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refreshLayout =findViewById(R.id.refresh_layout)
        var recycleView:RecyclerView=findViewById(R.id.rv_auto)
        var manager= LinearLayoutManager(this)
        recycleView.layoutManager=manager
        adapter= PoetryAdapter()
        adapter.bindToRecyclerView(recycleView)
        adapter.onItemClickListener= BaseQuickAdapter.OnItemClickListener { a, v, p ->PoetryDetailActivity.startMe(this@MainActivity, a.getItem(p) as Poetry) }
        var viewModel = ViewModelProviders.of(this).get(PoetryViewModel::class.java)

        refreshLayout.setOnRefreshListener {
            page=1;
            getWeather(page,viewModel)
        }

        refreshLayout.setOnLoadMoreListener {
            page++;
            getWeather(page,viewModel)
        }
        getWeather(1,viewModel)
        getAddress(viewModel)
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
        viewModel.getAddress()
                .observe(this, Observer<String>{s-> Log.e("find",s)});
    }
}
