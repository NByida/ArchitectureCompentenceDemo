package com.gmail.xuyimin1994.architecturecompentencedemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.LabelWrap
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Type
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.WordWrap
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Wrap
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
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
            type.value= PoertyNet.getInstance().getTypes(id)
        },{
            var bean= Wrap<Type>( -1)
            bean.msg= it.message!!
            type.value= bean
        })}
}