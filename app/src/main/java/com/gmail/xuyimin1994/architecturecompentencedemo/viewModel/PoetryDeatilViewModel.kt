package com.gmail.xuyimin1994.architecturecompentencedemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
import kotlinx.coroutines.launch

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.viewModel
 *yida
 *2019/10/30 0030
 **/
class PoetryDeatilViewModel :ViewModel(){
    var poetry=MutableLiveData<BaseBean>()

    fun getRecommend(poetryId:String,page:Int){
        launch({
            poetry.value=PoertyNet.getInstance().getRecommend(poetryId,page)
        },{
            var bean= BaseBean( -1)
            bean.msg= it.message!!
            poetry.value= bean
        })
    }

    fun launch(block: suspend () -> Unit, error: (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error.invoke(e)
        }
    }
}