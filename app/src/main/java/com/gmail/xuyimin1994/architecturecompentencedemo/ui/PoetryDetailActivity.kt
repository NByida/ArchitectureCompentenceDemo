package com.gmail.xuyimin1994. architecturecompentencedemo.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View.GONE
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
    lateinit var mycontext:String
    lateinit var viewModel: PoetryDeatilViewModel
    lateinit var adapter: RecommendAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PoetryDeatilViewModel::class.java)
        poetry=intent.getParcelableExtra("poetry")
        initRv()
        initObserver()
        viewModel.getRecommend(poetry.poetId.toString(),1)
        tv_title.setText(Html.fromHtml(poetry.name?.replace("</p> <p>","<br>")?.replace("（","*")?.replace("）","")))
        tv_potery_name.setText(Html.fromHtml(poetry.poet?.replace("</p> <p>","<br>")))
        context.text=poetry.getText()
        setClick()
    }

    fun setClick(){
        if(poetry.appreciation!!.length<5){
            lay_apprecate.visibility=GONE
        }
        if(poetry.notes!!.length<5){
            lay_notes.visibility=GONE
        }
        if(poetry.translate!!.length<5){
            lay_trans.visibility=GONE
        }
        lay_content.setOnClickListener {context.text=poetry.getText()}
        lay_apprecate.setOnClickListener { context.text=Html.fromHtml(poetry.appreciation?.replace("</p> <p>","<br>"))  }
        lay_notes.setOnClickListener {context.text=Html.fromHtml(poetry.notes?.replace("</p> <p>","<br>"))  }
        lay_trans.setOnClickListener {context.text=Html.fromHtml(poetry.translate?.replace("</p> <p>","<br>"))  }
    }

    fun initRv(){
        rv_recommend.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapter=RecommendAdapter(null)
        adapter.bindToRecyclerView(rv_recommend)
        adapter.onItemClickListener=  BaseQuickAdapter.OnItemClickListener{
                a,_,p->
            var p=a.getItem(p) as Poetry
            if(p.isTag==0){
                startMe(this as FragmentActivity, p)
            }
        }
    }

    fun initObserver(){
      var  observer= Observer {bean: BaseBean ->
            if(bean.statue==-1){
                ToastUtil.showToast(this,bean.msg)
                return@Observer
            }
            adapter.replaceData(gaddTag(bean.list!!))
            adapter.notifyDataSetChanged()
            if(bean.list!!.size==0){
                lay_recommend.visibility= GONE
            }
        }
        viewModel.poetry.observe(this,observer)
    }

    fun gaddTag(list: List<Poetry>?):List<Poetry>{
        var newlist= ArrayList<Poetry>()
        var iterator=list?.iterator()
        var tag:String=""
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