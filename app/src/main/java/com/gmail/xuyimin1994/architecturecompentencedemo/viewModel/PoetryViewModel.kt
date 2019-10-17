package com.gmail.xuyimin1994.architecturecompentencedemo.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
import kotlinx.coroutines.launch

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.viewModel
 *yida
 *2019/7/2 0002
 **/
class PoetryViewModel : ViewModel(){
    var weather = MutableLiveData<List<Poetry>>()

     fun getAllPoetry(page:Int){
         launch({
        weather.value=PoertyNet.getInstance().fetchAllPoetry(page).list
     },{}) }

      fun searchPoetryByName(name: String, page: Int) {
          launch({
          weather.value= PoertyNet.getInstance().searchPoetry(name, page).list
          },{})
      }

     fun searchAll(name: String, page: Int) {
         launch({
         weather.value = PoertyNet.getInstance().searchAll(name, page).list
         },{})
     }

    fun searchPoet(name: String, page: Int) {
        launch({
            weather.value = PoertyNet.getInstance().searchPoet(name, page).list
        },{})
    }

    fun searchContent(name: String, page: Int) {
        launch({
            weather.value = PoertyNet.getInstance().searchContent(name, page).list
        },{})
    }

    fun launch(block: suspend () -> Unit, error: (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error.invoke(e)
        }
    }

}