package com.gmail.xuyimin1994.architecturecompentencedemo.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.gmail.xuyimin1994.architecturecompentencedemo.R

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.utils
 *yida
 *2019/10/21 0021
 **/
class ToastUtil {

    /**
     * 获取Toast
     * @return
     */
    var toast: Toast? = null
        private set
    private var toastView: LinearLayout? = null

    /**
     * 修改原布局的Toast
     */
    private constructor() {

    }

    private object holder {
        internal var toastUtil = ToastUtil()
    }

    //    飞幕toast样式
    //    圆角 居中 白字 黑色背景 透明度80%

    fun setFeiMuToast(): ToastUtil {
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        val view = toast!!.view
        if (view != null) {
            val message = view.findViewById<TextView>(android.R.id.message)
            (message.parent as View).setBackgroundResource(R.drawable.bg_round_white)
            (message.parent as View).background.alpha = 200
            message.setTextColor(view.context.resources.getColor(R.color.white))
            view.layoutParams
        }
        return this
    }



    /**
     * 完全自定义布局Toast
     * @param context
     * @param view
     */
    constructor(context: Context, view: View, duration: Int) {
        toast = Toast(context)
        toast!!.view = view
        toast!!.duration = duration
    }

    /**
     * 向Toast中添加自定义view
     * @param view
     * @param postion
     * @return
     */
    fun addView(view: View, postion: Int): ToastUtil {
        toastView = toast!!.view as LinearLayout
        toastView!!.addView(view, postion)

        return this
    }

    /**
     * 设置Toast字体及背景颜色
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    fun setToastColor(messageColor: Int, backgroundColor: Int): ToastUtil {
        val view = toast!!.view
        if (view != null) {
            val message = view.findViewById<View>(android.R.id.message) as TextView
            message.setBackgroundColor(backgroundColor)
            message.setTextColor(messageColor)
        }
        return this
    }

    /**
     * 设置Toast字体及背景
     * @param messageColor
     * @param background
     * @return
     */
    fun setToastBackground(messageColor: Int, background: Int): ToastUtil {
        val view = toast!!.view
        if (view != null) {
            val message = view.findViewById<View>(android.R.id.message) as TextView
            (message.parent as View).setBackgroundResource(background)
            message.setTextColor(messageColor)
            view.layoutParams
        }
        return this
    }


    /**
     * 短时间显示Toast
     */
    fun Short(context: Context, message: CharSequence): ToastUtil {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        return this
    }

    /**
     * 短时间显示Toast
     */
    fun Short(context: Context, message: Int): ToastUtil {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        return this
    }

    /**
     * 长时间显示Toast
     */
    fun Long(context: Context, message: CharSequence): ToastUtil {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = Toast.LENGTH_LONG
        }

        return this
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    fun Long(context: Context, message: Int): ToastUtil {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = Toast.LENGTH_LONG
        }
        return this
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    fun Indefinite(context: Context, message: CharSequence, duration: Int): ToastUtil {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, duration)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = duration
        }
        return this
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    fun Indefinite(context: Context, message: Int, duration: Int): ToastUtil {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, duration)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = duration
        }
        return this
    }

    /**
     * 显示Toast
     * @return
     */
    fun show(): ToastUtil {
        toast!!.show()

        return this
    }

    companion object {

        val toastUtil: ToastUtil
            get() = holder.toastUtil

        fun showToast(context: Context, s: String) {
            ToastUtil().Short(context, s).setToastBackground(context.resources.getColor(R.color.white), R.drawable.bg_round_white).show()
        }
    }
}


