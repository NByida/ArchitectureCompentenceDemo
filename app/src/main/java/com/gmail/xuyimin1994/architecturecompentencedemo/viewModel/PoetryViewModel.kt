package com.gmail.xuyimin1994.architecturecompentencedemo.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
import com.gmail.xuyimin1994.architecturecompentencedemo.net.ServiceCreator
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.RegUtil.Companion.getAddress
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
    var address = MutableLiveData<String>()


    fun getAllPoetry(page:Int) :LiveData<List<Poetry>>{
        PoertyNet.getInstance().fetchAllPoetry(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    weather.value=response.list
                    Log.e("sub", response.list!![0].name)
                },{t->
                    ServiceCreator.regainAddress=true
                    Log.e("getpoetry onFailure", t.message)})
        return weather
    }


    fun getAddress():LiveData<String> {
        PoertyNet.getInstance().getNewAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ r -> address.value = getAddress(r) }, { t -> Log.e("getaddress onFailure", t.message) })
        return address
    }

        fun searchPoetryByName(name: String, page: Int): LiveData<List<Poetry>> {
            PoertyNet.getInstance().searchPoetry(name, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response -> weather.value = response.list }, { t -> Log.e("onFailure", "aa") })
            return weather;
        }

    fun searchAll(name: String, page: Int): LiveData<List<Poetry>> {
        PoertyNet.getInstance().searchAll(name, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> weather.value = response.list }, { t -> Log.e("onFailure", "aa") })
        return weather;
    }

    fun searchPoet(name: String, page: Int): LiveData<List<Poetry>> {
        PoertyNet.getInstance().searchPoet(name, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> weather.value = response.list }, { t -> Log.e("onFailure", "aa") })
        return weather;
    }


    fun searchContent(name: String, page: Int): LiveData<List<Poetry>> {
        PoertyNet.getInstance().searchContent(name, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> weather.value = response.list }, { t -> Log.e("onFailure", "aa") })
        return weather;
    }





}