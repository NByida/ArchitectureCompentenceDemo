package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.R


/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.adapter
 *yida
 *2019/10/31 0031
 **/
class RecommendAdapter(data: MutableList<Poetry>?) : BaseMultiItemQuickAdapter<Poetry, MyHolder>(data) {

    init {
        addItemType(1,R.layout.item_recommend_poetry)
        addItemType(2,R.layout.item_recommend_poetry_tag)
    }

    override fun convert(helper: MyHolder?, item: Poetry?) {
        if(item?.itemType==1){
            if (helper != null) {
                if (item != null) {
                    helper.setText(R.id.tv_title, Html.fromHtml(item.name))
                            .setText(R.id.tv_name, Html.fromHtml(item.poet))
                            .setText(R.id.context, item.getText())
                }
            }
        }else{
            helper?.setText(R.id.tag,item?.tag)
        }
    }
}