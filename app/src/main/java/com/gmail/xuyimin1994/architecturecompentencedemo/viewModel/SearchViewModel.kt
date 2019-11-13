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
 *2019/11/13 0013
 **/
class SearchViewModel:ViewModel() {
    var weather = MutableLiveData<BaseBean>()

    fun getPoetryByTag(tag:String,page:Int){
        launch({
            weather.value= PoertyNet.getInstance().getPoetryUnderTag(tag,page)
        },{
            var bean= BaseBean( -1)
            bean.msg= it.message!!
            weather.value= bean
        }) }

    fun launch(block: suspend () -> Unit, error: (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error.invoke(e)
        }
    }
}