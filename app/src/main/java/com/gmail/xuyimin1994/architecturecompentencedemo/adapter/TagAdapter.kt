package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Tag

class TagAdapter: BaseQuickAdapter<Tag, MyHolder>(R.layout.item_tag)  {
    override fun convert(helper: MyHolder?, item: Tag?) {
        helper?.setText(R.id.tv_tag,item?.tag?:"")
    }
}