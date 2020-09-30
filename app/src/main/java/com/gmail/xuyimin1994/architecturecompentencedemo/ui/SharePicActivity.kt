package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.FragmentViewPagerAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Word
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.BaseActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.dialog.ColorDialog
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.shareFragment.ShareFragmentCircle
import kotlinx.android.synthetic.main.share_activity.*

class SharePicActivity : BaseActivity() {

    companion object {
        var mword: Word?=null
        fun startMe(context: Activity,word: Word){
            mword=word
            context.startActivity(Intent(context,SharePicActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.share_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewPager()
        tvChangeColor.setOnClickListener {
            var colorDialog= ColorDialog.newDialog(3)
            colorDialog.show(supportFragmentManager)
        }
    }

    fun initViewPager(){
        var fragments=ArrayList<Fragment>()
        var mTabTitles= arrayOf(" ")
        fragments.add(ShareFragmentCircle.newInstance(mword!!))
        var mFragmentViewPageAdapter = FragmentViewPagerAdapter(supportFragmentManager, mTabTitles, fragments)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = mFragmentViewPageAdapter
    }
}