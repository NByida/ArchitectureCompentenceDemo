package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Label

class LabelAdapter : BaseQuickAdapter<Label, MyHolder>(R.layout.item_label){
    override fun convert(helper: MyHolder?, item: Label?) {
        helper?.setText(R.id.tvLabel,item?.label_name)
    }
}