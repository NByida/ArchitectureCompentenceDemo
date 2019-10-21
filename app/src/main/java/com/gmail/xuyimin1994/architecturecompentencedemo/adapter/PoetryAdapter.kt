package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry

class PoetryAdapter : BaseQuickAdapter<Poetry, MyHolder>(R.layout.item_poetry) {



    override fun convert(helper: MyHolder?, item: Poetry?) {
        if (helper != null) {
            if (item != null) {
                helper.setText(R.id.tv_title,Html.fromHtml(item.name))
                        .setText(R.id.tv_name,Html.fromHtml(item.poet))
                        .setText(R.id.context,Html.fromHtml(item.content?.replace("</p> <p>","<br>")))
            }
        }
    }
}