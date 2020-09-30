package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import com.gmail.xuyimin1994.architecturecompentencedemo.BuildConfig
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ThreadMangager
import com.gmail.xuyimin1994.architecturecompentencedemo.widget.launch
import com.umeng.analytics.MobclickAgent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_set_up.*
import java.util.concurrent.TimeUnit


class SetUpActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
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
                .subscribe{
                    ThreadMangager.excuteMain({
                        MainActivity.startMe(SetUpActivity@this)
                    })
//                    MingJuActivity.startMe(this)
                }
        text.text=getString(R.string.app_name)+BuildConfig.VERSION_NAME
    }

    public override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    public override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

}
