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
 *2019/7/2 0002
 **/
class PoetryViewModel : ViewModel(){
    var weather = MutableLiveData<BaseBean>()

    fun getAllPoetry(page:Int){
         launch({
             weather.value=PoertyNet.getInstance().fetchAllPoetry(page)
         },{
             var bean= BaseBean( -1)
             bean.msg= it.message!!
             weather.value= bean
         }) }

      fun searchPoetryByName(name: String, page: Int) {
          launch({
              weather.value= PoertyNet.getInstance().searchPoetry(name, page)
          },{
              var bean= BaseBean( -1)
              bean.msg= it.message!!
              weather.value= bean
          })
      }

     fun searchAll(name: String, page: Int) {
         launch({
             weather.value= PoertyNet.getInstance().searchAll(name, page)
         },{
             var bean= BaseBean( -1)
             bean.msg= it.message!!
             weather.value= bean
         })
     }

    fun searchPoet(name: String, page: Int) {
        launch({
            weather.value = PoertyNet.getInstance().searchPoet(name, page)
        },{
            var bean= BaseBean( -1)
            bean.msg= it.message!!
            weather.value= bean
        })
    }

    fun searchContent(name: String, page: Int) {
        launch({
            weather.value = PoertyNet.getInstance().searchContent(name, page)
            weather.value?.statue=1

        },{
            var bean= BaseBean( -1)
            bean.msg= it.message!!
            weather.value= bean
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