package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.PoetryAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.PoetryViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import java.util.ArrayList

class SearchActivity: AppCompatActivity() {

    companion object {
        fun startMe(context:AppCompatActivity){
            context.startActivity(Intent(context,SearchActivity::class.java))
        }
    }

    var page=1
    lateinit var adapter:PoetryAdapter
    lateinit var refreshLayout:SmartRefreshLayout
    lateinit var viewModel:PoetryViewModel
    lateinit var observer: Observer<List<Poetry>>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initObserver()
        refreshLayout =findViewById(R.id.refresh_layout)
        var recycleView: RecyclerView =findViewById(R.id.rv_auto)

        var editSearch: EditText =findViewById(R.id.edit_search)
        var tvDelete: TextView =findViewById(R.id.delete)
        tvDelete.setOnClickListener {
            editSearch.setText("")
        }

        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length == 0) {
                    tvDelete.setVisibility(View.GONE)
                } else
                    tvDelete.setVisibility(View.VISIBLE)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        viewModel = ViewModelProviders.of(this).get(PoetryViewModel::class.java)

        editSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var word:String=editSearch.text.toString()
                page=1
                search(word)
            }
            false
        }
        var manager= LinearLayoutManager(this)
        recycleView.layoutManager=manager
        adapter= PoetryAdapter()
        adapter.bindToRecyclerView(recycleView)
        adapter.onItemClickListener= BaseQuickAdapter.OnItemClickListener { a, v, p ->PoetryDetailActivity.startMe(this@SearchActivity, a.getItem(p) as Poetry) }
        refreshLayout.setOnRefreshListener {
            var word:String=editSearch.text.toString()
            page=1
            search(word)
        }
        refreshLayout.setOnLoadMoreListener {
            page++;
            var word:String=editSearch.text.toString()
            search(word)
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

    fun search(name:String){
        if(name.length==0)return
        viewModel.searchPoetryByName(name,page).observe(this, observer)
    }

}