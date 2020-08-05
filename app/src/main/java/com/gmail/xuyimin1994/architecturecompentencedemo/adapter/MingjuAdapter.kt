package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Word

class MingjuAdapter : BaseQuickAdapter<Word, MyHolder>(R.layout.item_mingju){
    override fun convert(helper: MyHolder?, item: Word?) {
        item?.words?.let {
            var strArray=it.split("，")
            if(strArray.size==2){
                helper?.setText(R.id.tv2,strArray[0])
                helper?.setText(R.id.tv1,strArray[1])
                helper?.setGone(R.id.tv1,true)
            }else{
                helper?.setText(R.id.tv2,it)
                helper?.setGone(R.id.tv1,false)
            }
        }
    }
}