package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.TagAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.TagWrap
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.RvFragmnet
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ToastUtil
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.TagViewModel
import kotlinx.android.synthetic.main.fragment_search_all.*

class TagsFragment:RvFragmnet() {

    override fun pullData(page: Int) {
        viewModel.getPoetryByTag(page)
    }

    lateinit var adapter: TagAdapter
    lateinit var viewModel: TagViewModel
    lateinit var observer: Observer<TagWrap>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TagViewModel::class.java)
        initRv()
        initObserver()
        parentCreated()
//        refreshLayout.autoRefresh()
    }

    fun initRv(){
        var manager= LinearLayoutManager(activity)
        rv_auto.layoutManager=manager
        adapter= TagAdapter()
        adapter.bindToRecyclerView(rv_auto)
        adapter.setEmptyView(R.layout.rv_empty)
    }

    fun initObserver(){
        observer= Observer {bean: TagWrap ->
            if(bean.statue==-1){
                if(page==1)refreshLayout.finishRefresh()else refreshLayout.finishLoadMore()
                ToastUtil.showToast(activity as Context,bean.msg)
                return@Observer
            }
            if(page==1){
                refreshLayout.finishRefresh()
                adapter.replaceData(bean.list!!)
                refreshLayout.setEnableLoadMore(true)
            }else{
                refreshLayout.finishLoadMore()
                adapter.addData(bean.list!!)
                refreshLayout.setEnableLoadMore(true)
            }
            adapter.notifyDataSetChanged()
            if(!bean.size.equals("10")){
                refreshLayout.setEnableLoadMore(false)
            }
        }
        viewModel.tags.observe(this,observer)
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_tag_all
    }
}