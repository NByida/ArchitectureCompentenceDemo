package com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import android.R
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.fragment.app.FragmentManager


open class BaseDialog(var layoutId:Int):DialogFragment() {

    var viewParent:View?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val window = dialog?.window
        val params = window?.attributes
        params?.dimAmount = 0f
        window?.attributes = params
        window?.setGravity(Gravity.CENTER)
//        params?.width=-2
//        params?.height=-2
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewParent=inflater.inflate(layoutId, container)
        return viewParent
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

     @Override
    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
    }

    fun show(fm: FragmentManager) {
        show(fm,this.javaClass.simpleName)
    }

    @Override
    override fun dismiss() {
        dismissAllowingStateLoss()
    }
}