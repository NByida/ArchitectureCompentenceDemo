package com.gmail.xuyimin1994.architecturecompentencedemo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.viewModel
 *yida
 *2019/9/17 0017
 **/
class PoetryViewModelFactroy(): ViewModelProvider.NewInstanceFactory()  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PoetryViewModelFactroy() as T
    }
}