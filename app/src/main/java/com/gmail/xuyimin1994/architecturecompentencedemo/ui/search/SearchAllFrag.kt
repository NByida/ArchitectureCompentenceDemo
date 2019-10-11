package com.gmail.xuyimin1994.architecturecompentencedemo.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.PoetryAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.RvFragmnet
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.PoetryViewModel
import kotlinx.android.synthetic.main.fragment_search_all.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.enums.SearchType
import com.gmail.xuyimin1994.architecturecompentencedemo.event.Default
import com.gmail.xuyimin1994.architecturecompentencedemo.event.Search
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.PoetryDetailActivity
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.ui.search
 *yida
 *2019/10/11 0011
 **/
@SuppressLint("ValidFragment")
class SearchAllFrag  constructor(var type : SearchType): RvFragmnet() {

    lateinit var adapter: PoetryAdapter
    lateinit var viewModel: PoetryViewModel
    lateinit var observer: Observer<List<Poetry>>
    lateinit var word:String

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_all
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PoetryViewModel::class.java)
        initRv()
        initObserver()
        parentCreated()
    }

    override fun pullData(page: Int) {
        search()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun default(event: Search) {
        word=event.word
        search()
    }

    fun initRv(){
        var manager= LinearLayoutManager(activity)
        rv_auto.layoutManager=manager
        adapter= PoetryAdapter()
        adapter.bindToRecyclerView(rv_auto)
        adapter.onItemClickListener= BaseQuickAdapter.OnItemClickListener { a, v, p -> PoetryDetailActivity.startMe(activity as FragmentActivity, a.getItem(p) as Poetry) }
        adapter.setEmptyView(R.layout.rv_empty)
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

    fun search(){
        if( word.length==0)return
        when(type){
            SearchType.TITLE->viewModel.searchPoetryByName(word,page).observe(this, observer)
            SearchType.ALL->viewModel.searchAll(word,page).observe(this, observer)
            SearchType.AUTHOR->viewModel.searchPoet(word,page).observe(this, observer)
            SearchType.CONTENT->viewModel.searchContent(word,page).observe(this, observer)
        }

    }
}