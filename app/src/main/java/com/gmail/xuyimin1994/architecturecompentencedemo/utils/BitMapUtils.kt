package com.gmail.xuyimin1994.architecturecompentencedemo.utils

import android.Manifest
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions3.RxPermissions

object BitMapUtils {
     fun savePicture(activity:FragmentActivity,imPost: ViewGroup,saveFinish:()->Unit={}) {
        try {
            imPost.buildDrawingCache()  //启用DrawingCache并创建位图
            val bitmap = Bitmap.createBitmap(imPost.getDrawingCache()) //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
            val rxPermissions = RxPermissions(activity)
            rxPermissions.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            ImageUtil.saveBitmap2File(bitmap, activity, "shareImage"+System.currentTimeMillis(), true)
                        } else {
                            ToastUtil.toastUtil.Short(activity,"请授予权限")
                        }
                        saveFinish()
                    }
            imPost.setDrawingCacheEnabled(false)
        } catch (e: Exception) {
            Log.e("",e.message)
            saveFinish()
        }
    }
}