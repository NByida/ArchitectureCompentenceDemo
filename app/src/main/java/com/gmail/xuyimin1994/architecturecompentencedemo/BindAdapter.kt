package com.gmail.xuyimin1994.architecturecompentencedemo

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("bind:loadBingPic")
fun ImageView.loadBingPic(url: String?) {
    if (url != null) Glide.with(context).load(url).into(this)
}