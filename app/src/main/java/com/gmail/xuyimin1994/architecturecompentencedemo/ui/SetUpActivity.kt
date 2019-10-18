package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class SetUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_up)
        Observable.interval(1, TimeUnit.SECONDS)
                .filter {it==1L}
                .takeUntil{it==1L}
                .subscribe{MainActivity.startMe(this)}
    }

}
