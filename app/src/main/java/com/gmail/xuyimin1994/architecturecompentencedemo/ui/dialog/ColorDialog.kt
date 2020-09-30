package com.gmail.xuyimin1994.architecturecompentencedemo.ui.dialog

import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.BaseDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.ColorAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.event.ChangeColor
import kotlinx.android.synthetic.main.dialog_choose_color.rv_auto
import kotlinx.android.synthetic.main.dialog_choose_color.rv_type
import org.greenrobot.eventbus.EventBus


class ColorDialog :BaseDialog(R.layout.dialog_choose_color){
    var colorListName= ArrayList<String>()
    var colorListValue= ArrayList<String>()
    var colorListName2= ArrayList<String>()
   var colorAdapter: ColorAdapter=ColorAdapter()
   var colorAdapter2: ColorAdapter=ColorAdapter()

    /**
     * 1-->背景
     * 2-->文字
     * 3-->配图
     */
    companion object {
        var type:Int=1
        fun newDialog(type_:Int):ColorDialog{
            type=type_
            return  ColorDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    fun initRv(){
        var manager= GridLayoutManager(context,4)
        var manager2= GridLayoutManager(context,4)
        rv_auto.layoutManager=manager
        rv_type.layoutManager=manager2
        colorAdapter?.bindToRecyclerView(rv_auto)
        colorAdapter2?.bindToRecyclerView(rv_type)
        colorAdapter?.setOnItemClickListener{a,_,p->
            var name=a.data[p]
            colorListValue.forEach {
                if (it.trim().isEmpty()){
                    colorListName2.add("${name}")
                }else colorListName2.add("${name}_${it}")
            }
            colorAdapter2.replaceData(colorListName2)
            rv_type.visibility=View.VISIBLE
            rv_auto.visibility=View.GONE
        }
        colorAdapter2?.setOnItemClickListener{a,_,p->
            EventBus.getDefault().post(ChangeColor(type,a.data[p].toString()))
            dismiss()
        }
        initColor()
    }

    fun initColor(){
        var res: Resources = resources
        var colorValue = res.getStringArray(R.array.color_value)
        var colorName = res.getStringArray(R.array.color_name)
        colorName.forEach {
            colorListName.add("${it}")
        }
        colorValue.forEach {
            colorListValue.add("${it}")
        }
        colorAdapter.addData(colorListName)
    }



}