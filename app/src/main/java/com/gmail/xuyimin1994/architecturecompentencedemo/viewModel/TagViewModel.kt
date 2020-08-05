package com.gmail.xuyimin1994.architecturecompentencedemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.gmail.xuyimin1994.architecturecompentencedemo.entity.TagWrap
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
import kotlinx.coroutines.launch

class TagViewModel:ViewModel() {
    var tags = MutableLiveData<TagWrap>()

    fun getPoetryByTag(page:Int){
        launch({
            tags.value= PoertyNet.getInstance().getAllTag(page)
        },{
            var bean= TagWrap( -1)
            bean.msg= it.message!!
            tags.value= bean
        }) }

    fun ViewModel.launch(block: suspend () -> Unit, error: (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error.invoke(e)
        }
    }
}