package com.gmail.xuyimin1994.architecturecompentencedemo.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Word
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ColorUtlis

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
        var tv=helper?.getView<TextView>(R.id.tv1)
        var tv2=helper?.getView<TextView>(R.id.tv2)
        item?.color?.let {
            if(it.length==0){
                tv?.setTextColor(ContextCompat.getColor(tv.context,R.color.black))
                tv2?.setTextColor(ContextCompat.getColor(tv2.context,R.color.black))
            }else{
                tv?.setTextColor(ContextCompat.getColor(tv.context , ColorUtlis.getIdByName(it)))
                tv2?.setTextColor(ContextCompat.getColor(tv2.context , ColorUtlis.getIdByName(it)))
            }
        }?:let {
            tv?.setTextColor(ContextCompat.getColor(tv.context,R.color.black))
            tv2?.setTextColor(ContextCompat.getColor(tv2.context,R.color.black))
        }
    }
}