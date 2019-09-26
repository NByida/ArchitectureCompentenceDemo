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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.rv_auto
import kotlinx.android.synthetic.main.activity_search.*
import java.util.ArrayList

class SearchActivity: RvActivity() {

    override fun pullData(page: Int) {
        search()
    }

    companion object {
        fun startMe(context:AppCompatActivity){
            context.startActivity(Intent(context,SearchActivity::class.java))
        }
    }

    lateinit var adapter:PoetryAdapter
    lateinit var viewModel:PoetryViewModel
    lateinit var observer: Observer<List<Poetry>>
    lateinit var   word:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initObserver()
        viewModel = ViewModelProviders.of(this).get(PoetryViewModel::class.java)
        initView()
        initRv()
        parentCreated()
    }

    fun initView(){
        delete.setOnClickListener {
            edit_search.setText("")
        }

        edit_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length == 0) {
                    delete.setVisibility(View.GONE)
                } else
                    delete.setVisibility(View.VISIBLE)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        edit_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                word=edit_search.text.toString()
                page=1
                search()
            }
            false
        }
    }

    fun initRv(){
        var manager= LinearLayoutManager(this)
        rv_auto.layoutManager=manager
        adapter= PoetryAdapter()
        adapter.bindToRecyclerView(rv_auto)
        adapter.onItemClickListener= BaseQuickAdapter.OnItemClickListener { a, v, p ->PoetryDetailActivity.startMe(this@SearchActivity, a.getItem(p) as Poetry) }
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
        viewModel.searchPoetryByName(word,page).observe(this, observer)
    }

}