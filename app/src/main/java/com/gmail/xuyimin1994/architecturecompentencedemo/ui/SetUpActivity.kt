package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class SetUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
        setContentView(R.layout.activity_set_up)
        Observable.interval(1, TimeUnit.SECONDS)
                .filter {it==1L}
                .takeUntil{it==1L}
                .subscribe{MainActivity.startMe(this)}
    }

}
