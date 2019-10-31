package com.gmail.xuyimin1994.architecturecompentencedemo.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
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
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.enums.SearchType
import com.gmail.xuyimin1994.architecturecompentencedemo.event.Search
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.PoetryDetailActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ToastUtil
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
    lateinit var observer: Observer<BaseBean>
    var word:String?=null

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
        adapter.onItemClickListener= BaseQuickAdapter.OnItemClickListener { a, v, p ->
            var view =v as View
            val pair = Pair<View, String>(view.findViewById(R.id.tv_title), "name")
            val pair2 = Pair<View, String>( view.findViewById(R.id.tv_name)!!, "auth")
            val pair3 = Pair<View, String>(view.findViewById(R.id.context)!!, "context")
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as FragmentActivity, pair,pair2,pair3)
            PoetryDetailActivity.startMe(activity as FragmentActivity, a.getItem(p) as Poetry,optionsCompat)
        }
        adapter.setEmptyView(R.layout.rv_empty)
    }

    fun initObserver(){
        observer= Observer {bean:BaseBean->
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
        viewModel.weather.observe(this,observer)
    }

    fun search(){
        if (word==null){
            refreshLayout.finishRefresh()
            return
        }
        when(type){
            SearchType.TITLE->viewModel.searchPoetryByName(word!!,page)
            SearchType.ALL->viewModel.searchAll(word!!,page)
            SearchType.AUTHOR->viewModel.searchPoet(word!!,page)
            SearchType.CONTENT->viewModel.searchContent(word!!,page)
        }
    }
}