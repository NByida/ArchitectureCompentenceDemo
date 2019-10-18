package com.gmail.xuyimin1994.architecturecompentencedemo.ui


import android.app.Activity
import android.content.Intent
import android.os.Bundle


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.PoetryAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.RvActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.search.SearchActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.PoetryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe




class MainActivity : RvActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    companion object {
        fun startMe(context: Activity){
            context.startActivity(Intent(context,MainActivity::class.java))
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun refreshSignTime(event: Observer<String>) {

    }

    lateinit var adapter:PoetryAdapter
    lateinit var viewModel:PoetryViewModel
    lateinit var observer: Observer<List<Poetry>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PoetryViewModel::class.java)
        initObserver()
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
         viewModel.getAllPoetry(page)
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
        viewModel.weather.observe(this,observer)
    }
}
