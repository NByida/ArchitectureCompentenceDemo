package com.gmail.xuyimin1994.architecturecompentencedemo.ui


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.PoetryAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.RvActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.search.SearchActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.PoetryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ToastUtil


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
    lateinit var observer: Observer<BaseBean>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PoetryViewModel::class.java)
        initObserver()
        parentCreated()
        initRv()
        pullData(page)
    }

    var time:Long=0L
    override fun onBackPressed() {
        if(time==0L){
            time=System.currentTimeMillis()
        }else if(System.currentTimeMillis()-time<1000){
            super.onBackPressed()
            time=0
            return
        }
        time=System.currentTimeMillis()
        ToastUtil.showToast(this,"再按一次退出沓诗词~")
    }

    fun initRv(){
        var manager= LinearLayoutManager(this)
        rv_auto.layoutManager=manager
        adapter= PoetryAdapter()
        adapter.bindToRecyclerView(rv_auto)
        adapter.onItemClickListener= BaseQuickAdapter.OnItemClickListener { a, v, p ->
            var view =v as View
            val pair = Pair<View, String>(view.findViewById(R.id.tv_title), "name")
            val pair2 = Pair<View, String>( view.findViewById(R.id.tv_name)!!, "auth")
            val pair3 = Pair<View, String>(view.findViewById(R.id.context)!!, "context")
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair,pair2,pair3)
            PoetryDetailActivity.startMe(this@MainActivity, a.getItem(p) as Poetry,optionsCompat)
         }
        tv_search.setOnClickListener {
            SearchActivity.startMe(this)
        }
    }

     override fun pullData(page: Int) {
         viewModel.getAllPoetry(page)
     }

    fun initObserver(){
        observer= Observer {bean:BaseBean->
            if(bean.statue==-1){
                if(page==1)refreshLayout.finishRefresh()else refreshLayout.finishLoadMore()
                Log.e("error",bean.msg)
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

        }
        viewModel.weather.observe(this,observer)
    }
}
