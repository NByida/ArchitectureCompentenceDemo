package com.gmail.xuyimin1994.architecturecompentencedemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.xuyimin1994.architecturecompentencedemo.app.App
import com.gmail.xuyimin1994.architecturecompentencedemo.app.App.Companion.context
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.*
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.SharedPreferenceUtil
import kotlinx.coroutines.launch

class MingJuViewModel:ViewModel() {
    var mingJu = MutableLiveData<WordWrap>()

    var type = MutableLiveData<Wrap<Type>>()
    var label = MutableLiveData<LabelWrap>()

    fun getMingJu(page:Int,type:String=""){
        launch({
            if(type.isEmpty()){
                mingJu.value= PoertyNet.getInstance().getMingJu(page)
            }else{
                mingJu.value= PoertyNet.getInstance().getMingJuByType(type,page)
            }
        },{
            var bean= WordWrap( -1)
            bean.msg= it.message!!
            mingJu.value= bean
        }) }


    fun getLabel(){
        launch({
            label.value= PoertyNet.getInstance().getLabel(1)
        },{
            var bean= LabelWrap( -1)
            bean.msg= it.message!!
            label.value= bean
    })}

    fun ViewModel.launch(block: suspend () -> Unit, error: (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error.invoke(e)
        }
    }

    fun getType(id:String){
        launch({
            var warp=PoertyNet.getInstance().getTypes(id)
            warp.msg=id
            type.value=warp
        },{
            var bean= Wrap<Type>( -1)
            bean.msg= id
            type.value= bean

        })}
}