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
    lateinit var observer: Observer<List<Poetry>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initObserver()
        viewModel = ViewModelProviders.of(this).get(PoetryViewModel::class.java)
        parentCreated()
        initRv()
        pullData(page)
    }

    fun initRv(){
        var manager= LinearLayoutManager(this)
        rv_auto.layoutManager=manager
        adapter= PoetryAdapter()
        adapter.bindToRecyclerView(rv_auto)
        adapter.onItemClickListener= BaseQuickAdapter.OnItemClickListener { a, v, p ->PoetryDetailActivity.startMe(this@MainActivity, a.getItem(p) as Poetry) }
        tv_search.setOnClickListener {
            SearchActivity.startMe(this)
        }
    }

    override fun pullData(page: Int) {
        getWeather(page,viewModel)
        if (page==1){
            getAddress(viewModel)
        }
    }

    fun initObserver(){
        observer= Observer {list:List<Poetry>->
            if(page==1){
                refreshLayout.finishRefresh()
                adapter.replaceData(list)
                refreshLayout.setEnableLoadMore(true)
            }else{
                refreshLayout.finishLoadMore()
                adapter.addData(list)
                refreshLayout.setEnableLoadMore(true)
            }
            adapter.notifyDataSetChanged()
        }
    }


    fun getWeather(page:Int,viewModel:PoetryViewModel){
        viewModel.getAllPoetry(page).observe(this, observer)
    }

    fun getAddress(viewModel:PoetryViewModel){
        viewModel.getAddress().observe(this, Observer<String>{s->
            SharedPreferenceUtil.getInstance().put(this,"address",if(s.contains("https")){s}else{s.replace("http","https")})
            Log.e("address",s+"")
        })
    }
}
