package com.gmail.xuyimin1994.architecturecompentencedemo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.viewModel
 *yida
 *2019/7/2 0002
 **/
class PoetryViewModel : ViewModel(){
    var weather = MutableLiveData<List<Poetry>>()

    fun getWeather(page:Int) :LiveData<List<Poetry>>{
        PoertyNet.getInstance().fetchAllPoetry(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> weather.value=response.list},{t-> Log.e("onFailure", "aa")})
        return weather;
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }

}