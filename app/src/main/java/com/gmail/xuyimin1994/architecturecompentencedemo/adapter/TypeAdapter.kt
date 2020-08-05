package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Type

class TypeAdapter: BaseQuickAdapter<Type, MyHolder>(R.layout.item_label){
    override fun convert(helper: MyHolder?, item: Type?) {
        helper?.setText(R.id.tvLabel,item?.type)
    }
}