package com.gmail.xuyimin1994. architecturecompentencedemo.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.BaseActivity
import kotlinx.android.synthetic.main.activity_potery_detail.*
import kotlinx.android.synthetic.main.activity_potery_detail.context
import kotlinx.android.synthetic.main.activity_potery_detail.tv_title
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.RecommendAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ToastUtil
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.PoetryDeatilViewModel


class PoetryDetailActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_potery_detail
    }

    lateinit var poetry:Poetry
    companion object {
        fun startMe(context: Activity, poetry:Poetry,activityOptionsCompat:ActivityOptionsCompat){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(Intent(context,PoetryDetailActivity::class.java).putExtra("poetry",poetry),activityOptionsCompat.toBundle())
            }else{
                context.startActivity(Intent(context,PoetryDetailActivity::class.java).putExtra("poetry",poetry))
            }
        }
        fun startMe(context: Activity, poetry:Poetry){
            context.startActivity(Intent(context,PoetryDetailActivity::class.java).putExtra("poetry",poetry))
        }
    }
    lateinit var viewModel: PoetryDeatilViewModel
    lateinit var adapter: RecommendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRv()
        initObserver()
        poetry=intent.getParcelableExtra("poetry")
        viewModel.getRecommend(poetry.poetId.toString(),1)
        setPoetryUi()
    }

    fun setPoetryUi(){
        tv_title.text = poetry.getTitle()
        tv_potery_name.text=poetry.getPoetName()
        context.text=poetry.getText()
        setClick()
    }

    fun setClick(){
        if(poetry.appreciation!!.length<5){ lay_apprecate.visibility=GONE }
        if(poetry.notes!!.length<5){ lay_notes.visibility=GONE }
        if(poetry.translate!!.length<5){ lay_trans.visibility=GONE }
        if(poetry.appreciation!!.length<5&&poetry.notes!!.length<5&&poetry.translate!!.length<5){
            lay_content.visibility= GONE
        }
        lay_content.setOnClickListener {context.text=poetry.getText()
            tv_content.setTextColor(resources.getColor(R.color.colorAccent))
            tv_trans.setTextColor(resources.getColor(R.color.black))
            tv_note.setTextColor(resources.getColor(R.color.black))
            tv_appracate.setTextColor(resources.getColor(R.color.black))
            scroll_view.smoothScrollTo(0,0)
        }
        lay_apprecate.setOnClickListener { context.text=poetry.getApprecate()
            tv_appracate.setTextColor(resources.getColor(R.color.colorAccent))
            tv_trans.setTextColor(resources.getColor(R.color.black))
            tv_content.setTextColor(resources.getColor(R.color.black))
            tv_note.setTextColor(resources.getColor(R.color.black))
            scroll_view.smoothScrollTo(0,0)
        }
        lay_notes.setOnClickListener {context.text=poetry.getnotes()
            tv_note.setTextColor(resources.getColor(R.color.colorAccent))
            tv_trans.setTextColor(resources.getColor(R.color.black))
            tv_content.setTextColor(resources.getColor(R.color.black))
            tv_appracate.setTextColor(resources.getColor(R.color.black))
            scroll_view.smoothScrollTo(0,0)
        }
        lay_trans.setOnClickListener {context.text=poetry.getTrans()
            tv_trans.setTextColor(resources.getColor(R.color.colorAccent))
            tv_note.setTextColor(resources.getColor(R.color.black))
            tv_content.setTextColor(resources.getColor(R.color.black))
            tv_appracate.setTextColor(resources.getColor(R.color.black))
            scroll_view.smoothScrollTo(0,0)
        }
        var showOpition=true
        context.setOnClickListener {
           if (showOpition)  {
               lay_recommend.visibility=GONE
               lay_opercate.visibility=GONE
           }else  {
               if(adapter.data.size>0){
                   lay_recommend.visibility=VISIBLE
               }
               lay_opercate.visibility=VISIBLE
           }
            showOpition=!showOpition
        }
    }

    fun initRv(){
        rv_recommend.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapter=RecommendAdapter(null)
        adapter.bindToRecyclerView(rv_recommend)
        adapter.onItemClickListener=  BaseQuickAdapter.OnItemClickListener{a,_,p->
            var p=a.getItem(p) as Poetry
            if(p.isTag==0){
                startMe(this as FragmentActivity, p)
            }}
    }

    fun initObserver(){
        viewModel = ViewModelProviders.of(this).get(PoetryDeatilViewModel::class.java)
        var  observer= Observer {bean: BaseBean ->
            if(bean.statue==-1){
                ToastUtil.showToast(this,bean.msg)
                return@Observer
            }
            adapter.replaceData(gaddTag(bean.list!!))
            adapter.notifyDataSetChanged()
            if(bean.list!!.size==0){
                lay_recommend.visibility= GONE
            }else lay_recommend.visibility= VISIBLE
        }
        viewModel.poetry.observe(this,observer)
    }

    /**
     * 为诗词添加分类标签
     */
    fun gaddTag(list: List<Poetry>?):List<Poetry>{
        var newlist= ArrayList<Poetry>()
        var iterator=list?.iterator()
        var tag=""
        if(list!!.size>0){
            var p=Poetry()
            p.isTag=1
            p.tag="相关推荐"
            newlist.add(p)
        }
        while (iterator!!.hasNext()){
            var poetry:Poetry=iterator.next()
            if(!poetry.tag.equals(tag)){
                var p=Poetry()
                p.isTag=1
                p.tag=poetry.tag
                newlist.add(p)
            }
            newlist.add(poetry)
            tag=poetry.tag!!
        }
        return newlist
    }
}