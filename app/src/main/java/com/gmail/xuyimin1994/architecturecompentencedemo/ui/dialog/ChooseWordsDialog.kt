package com.gmail.xuyimin1994.architecturecompentencedemo.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.LabelAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.TypeAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.app.App
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Label
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.LabelWrap
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Type
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Wrap
import com.gmail.xuyimin1994.architecturecompentencedemo.event.GetWord
import com.gmail.xuyimin1994.architecturecompentencedemo.event.Search
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.BaseDialog
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.SharedPreferenceUtil
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ToastUtil
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.MingJuViewModel
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_mingju.*
import kotlinx.android.synthetic.main.activity_mingju.rv_auto
import kotlinx.android.synthetic.main.dialog_choose_word.*
import org.greenrobot.eventbus.EventBus

class ChooseWordsDialog:BaseDialog(R.layout.dialog_choose_word) {
    lateinit var viewModel: MingJuViewModel
    var labelAdapter: LabelAdapter?=null
    var typeAdapter: TypeAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MingJuViewModel::class.java)
        initRv()
        initObserver()
        getLabelData()
    }

    fun getLabelData(){
        var time:Long=SharedPreferenceUtil.getInstance(App.context).get("label_time",System.currentTimeMillis()) as Long
        if(System.currentTimeMillis()-time>48*3600000L){
            viewModel.getLabel()
            return
        }
        var labelLis=SharedPreferenceUtil.getInstance(App.context).getObject("label",ArrayList::class.java as Class<MutableList<Any>>)
        labelLis?.size?.let {
            if(it<=0){
                viewModel.getLabel()
            }else{
                var list=ArrayList<Label>()
                labelLis.forEach {
                    var treeMap=it as? LinkedTreeMap<String,String>
                    var label_name=treeMap?.get("label_name")
                    var id=treeMap?.get("id")
                    list.add(Label(label_name!!,id!!))
                }
                labelAdapter?.replaceData(list)
                SharedPreferenceUtil.getInstance(App.context).put("label_time",System.currentTimeMillis())
            }
        }?:let {
            viewModel.getLabel()
        }
    }

    fun getTypeData(type:String){
        var time:Long=SharedPreferenceUtil.getInstance(App.context).get("Type_time_${type}",System.currentTimeMillis()) as Long
        if(System.currentTimeMillis()-time>48*3600000L){
            viewModel.getType(type)
            return
        }
        var labelLis=SharedPreferenceUtil.getInstance(App.context).getObject("type_${type}",ArrayList::class.java as Class<MutableList<Any>>)
        labelLis?.size?.let {
            if(it<=0){
                viewModel.getType(type)
            }else{
                var list=ArrayList<Type>()
                labelLis.forEach {
                    var treeMap=it as? LinkedTreeMap<String,String>
                    var label_name=treeMap?.get("label_name")
                    var label_id=treeMap?.get("label_id")
                    var type=treeMap?.get("type")
                    list.add(Type(label_name!!,label_id!!,type!!))
                }
                typeAdapter?.replaceData(list)
                rv_type.visibility=View.VISIBLE
                rv_auto.visibility=View.GONE
                SharedPreferenceUtil.getInstance(App.context).put("Type_time_${type}",System.currentTimeMillis())
            }
        }?:let {
            viewModel.getType(type)
        }
    }

    fun initRv(){
        labelAdapter=LabelAdapter()
        typeAdapter=TypeAdapter()
        var manager= GridLayoutManager(context,4)
        var manager2= GridLayoutManager(context,4)
        rv_auto.layoutManager=manager
        rv_type.layoutManager=manager2
        labelAdapter?.bindToRecyclerView(rv_auto)
        typeAdapter?.bindToRecyclerView(rv_type)
        labelAdapter?.setOnItemClickListener{a,_,p-> getTypeData((a.data[p] as Label).id) }
        typeAdapter?.setOnItemClickListener{a,_,p->
            val type=(a.data[p] as Type).type
            viewModel.getType(type)
            EventBus.getDefault().post(GetWord(type))
            dismiss()
        }
    }

    lateinit var observer: Observer<LabelWrap>
    lateinit var observerType: Observer<Wrap<Type>>
    val label="label"
    val type="type"

    fun initObserver(){
        observer= Observer {bean: LabelWrap ->
            if(bean.statue==-1){
                ToastUtil.showToast(context,bean.msg)
                return@Observer
            }
            labelAdapter?.replaceData(bean.list!!)
            labelAdapter?.notifyDataSetChanged()
            SharedPreferenceUtil.getInstance(context).putObject<Any>(label,bean.list)
        }

        observerType= Observer {bean: Wrap<Type> ->
            if(bean.statue==-1){
                ToastUtil.showToast(context,bean.msg)
                return@Observer
            }
            typeAdapter?.replaceData(bean.list!!)
            typeAdapter?.notifyDataSetChanged()
            rv_type.visibility=View.VISIBLE
            rv_auto.visibility=View.GONE
            SharedPreferenceUtil.getInstance(App.context).putObject("type_${bean.msg}",bean.list)
        }
        viewModel.label.observe(this,observer)
        viewModel.type.observe(this,observerType)
    }


}