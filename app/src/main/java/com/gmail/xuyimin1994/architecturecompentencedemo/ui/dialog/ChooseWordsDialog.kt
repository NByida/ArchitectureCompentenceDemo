package com.gmail.xuyimin1994.architecturecompentencedemo.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.LabelAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.TypeAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Label
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.LabelWrap
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Type
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Wrap
import com.gmail.xuyimin1994.architecturecompentencedemo.event.GetWord
import com.gmail.xuyimin1994.architecturecompentencedemo.event.Search
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.BaseDialog
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ToastUtil
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.MingJuViewModel
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
        viewModel.getLabel()
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
        labelAdapter?.setOnItemClickListener{a,_,p-> viewModel.getType((a.data[p] as Label).id) }
        typeAdapter?.setOnItemClickListener{a,_,p->
            val type=(a.data[p] as Type).type
            viewModel.getType(type)
            EventBus.getDefault().post(GetWord(type))
            dismiss()
        }
    }

    lateinit var observer: Observer<LabelWrap>
    lateinit var observerType: Observer<Wrap<Type>>

    fun initObserver(){
        observer= Observer {bean: LabelWrap ->
            if(bean.statue==-1){
                ToastUtil.showToast(context,bean.msg)
                return@Observer
            }
            labelAdapter?.replaceData(bean.list!!)
            labelAdapter?.notifyDataSetChanged()
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
        }
        viewModel.label.observe(this,observer)
        viewModel.type.observe(this,observerType)
    }


}