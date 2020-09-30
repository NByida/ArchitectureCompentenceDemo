package com.gmail.xuyimin1994.architecturecompentencedemo.ui.shareFragment

import android.Manifest
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Word
import com.gmail.xuyimin1994.architecturecompentencedemo.event.ChangeColor
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.BaseFragment
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.*
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.activity_mingju.view.*
import kotlinx.android.synthetic.main.fragment_share_circle.*
import kotlinx.android.synthetic.main.fragment_share_circle.save
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ShareFragmentCircle:BaseFragment() {

    companion object{
        var mWord: Word?=null
        fun newInstance(word: Word):ShareFragmentCircle{
            mWord=word
            return ShareFragmentCircle()
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_share_circle
    }

    fun openpic(){
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: List<LocalMedia>) {
                        result?.get(0)?.let {
                            shareIamge.setImageDrawable(BitmapDrawable(ImageUtil.getImageBitmap(it.realPath, DisplayUtils.getScreenWidth(context).toFloat(), DisplayUtils.getScreenHeight(context).toFloat())))
                        }
                    }

                    override fun onCancel() {
                    }
                })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareIamge.setOnClickListener {
            var  rxPermissions =  RxPermissions(requireActivity())
            rxPermissions.request(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            openpic()
                        } else {
                            ToastUtil.toastUtil.Short(requireActivity(),"请授予权限").show()
                        }
                    }
        }
        save.setOnClickListener {  BitMapUtils.savePicture(requireActivity(),laySave) }
        setWord(mWord)
    }

    fun setWord(item: Word?){
        item?.code.let {
            if(it==-1){
                tv2.text=StringUtils.getText(item!!.words)
                tv1.isVisible=false
                return
            }
        }
        item?.words?.let{
            val strArray=it.split("，")
            if(strArray.size==2){
                tv1.text=strArray[0]
                tv2.text=strArray[1]
                tv1.isVisible=true
            }else{
                tv2.text=it
                tv1.isVisible=false
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun change(event: ChangeColor) {
        when(event.type){
            3->{
                tv1.setTextColor(ContextCompat.getColor(requireContext() , ColorUtlis.getIdByName(event.color)))
                tv2.setTextColor(ContextCompat.getColor(requireContext() , ColorUtlis.getIdByName(event.color)))
            }
        }
    }

}