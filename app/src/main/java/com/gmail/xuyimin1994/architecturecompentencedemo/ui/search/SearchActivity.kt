package com.gmail.xuyimin1994.architecturecompentencedemo.ui.search

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.FlowItemClick
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.NexFlowLayoutAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.PoetryAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.enums.SearchType
import com.gmail.xuyimin1994.architecturecompentencedemo.event.Search
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.BaseActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.RvActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.Constants.SEARCH_WORD
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.SharedPreferenceUtil
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.PoetryViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_search.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity: BaseActivity() {

    lateinit var word:String
    internal val mFragments = arrayOfNulls<Fragment>(4)


    companion object {
        fun startMe(context:AppCompatActivity){
            context.startActivity(Intent(context, SearchActivity::class.java))
        }
    }



    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initFragments()
        initPagerIndicator()
    }

    fun initView(){
        delete.setOnClickListener {
            edit_search.setText("")
            lay_recommend.visibility= VISIBLE
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
                EventBus.getDefault().post(Search(word))
                lay_recommend.visibility=GONE

                if(word.isNotEmpty()){
                    var list=SharedPreferenceUtil.getInstance(this).getObject<ArrayList<String>>(SEARCH_WORD,ArrayList::class.java as Class<ArrayList<String>>)
                    list?.let {
                        if(list.size>=10){
                            list.removeAt(0)
                        }
                        val iterator=it.listIterator()
                        while (iterator.hasNext()){
                            val string=iterator.next()
                            if(word==string)iterator.remove()
                        }
                        list.add(word)
                    }?:let{
                        list= arrayListOf()
                        list.add(word)
                    }
                    SharedPreferenceUtil.getInstance(this).putObject(SEARCH_WORD,list)
                }
            }
            false
        }
        val flowAdapter: NexFlowLayoutAdapter=NexFlowLayoutAdapter(flow_history,null,false)
        flowAdapter.setNeedReverse(true)
        //kotlin Arraylist class获取太奇葩了
        val list=SharedPreferenceUtil.getInstance(this).getObject<ArrayList<String>>(SEARCH_WORD,ArrayList::class.java as Class<ArrayList<String>>)
        flowAdapter.setArrayList(list)
        flowAdapter.setFlowItemClick(object : FlowItemClick {
            override fun onclick(toString: String) {
                EventBus.getDefault().post(Search(toString))
                lay_recommend.visibility=GONE
            }
        })
    }



    fun initPagerIndicator(){
        var titleList= ArrayList<String>()
        titleList.add("全部")
        titleList.add("标题")
        titleList.add("作者")
        titleList.add("原文")
        var commonNavigator= CommonNavigator(this)

        fun ColorTransitionPagerTitleView.setTitlePadding(titlePadding: Int){
            val padding = UIUtil.dip2px(getContext(), (titlePadding/2).toDouble())
            this.setPadding(padding, 0, padding, 0)
            invalidate()
        }
        commonNavigator.adapter=object: CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY)
                colorTransitionPagerTitleView.setTitlePadding(40)
                colorTransitionPagerTitleView.setSelectedColor(resources.getColor(R.color.color_EC514E))
                colorTransitionPagerTitleView.setText(titleList.get(index))
                colorTransitionPagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                colorTransitionPagerTitleView.setOnClickListener({ v -> view_pager_content.setCurrentItem(index) })
                return colorTransitionPagerTitleView
            }

            override fun getCount(): Int {
                return titleList.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.setColors(resources.getColor(R.color.color_EC514E))
                indicator.roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
                indicator.lineWidth = UIUtil.dip2px(context, 32.0).toFloat()
                indicator.lineHeight = UIUtil.dip2px(context, 1.0).toFloat()
                return indicator
            }
        }
        magic_indicator.navigator=commonNavigator
        commonNavigator.notifyDataSetChanged()
        ViewPagerHelper.bind(magic_indicator, view_pager_content)
    }
    fun initFragments(){
        mFragments[0]=SearchAllFrag(SearchType.ALL)
        mFragments[1]=SearchAllFrag(SearchType.TITLE)
        mFragments[2]=SearchAllFrag(SearchType.AUTHOR)
        mFragments[3]=SearchAllFrag(SearchType.CONTENT)
        view_pager_content.setOffscreenPageLimit(4)
        view_pager_content.adapter=object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                    return mFragments[position] as Fragment
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }
        view_pager_content.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                view_pager_content.setCurrentItem(position)
                magic_indicator.onPageSelected(position)
            }
        })
    }

}