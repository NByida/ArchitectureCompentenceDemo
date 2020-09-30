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
//    【浣溪沙】<br><br>谁念西风独自凉<br>萧萧黄叶闭疏窗<br>沉思往事立残阳<br><br>被酒莫惊春睡重<br>赌书消得泼茶香<br>当时只道是寻常<br><br>
    fun setWord(item: Word?){
        item?.words?.let{
            val strArray=it
                    .replace("<br><br>","<br>")
                    .replace("【","<br>")
                    .replace("】","<br>")
                    .split("，","。","!","?","，","？","<br>")
            poetyView.setStringArray(strArray)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun change(event: ChangeColor) {
        when(event.type){
            3->{
                poetyView.setColor(ContextCompat.getColor(requireContext() , ColorUtlis.getIdByName(event.color)))
            }
            4->{
                laySave.setBackgroundColor(ContextCompat.getColor(requireContext() , ColorUtlis.getIdByName(event.color)))
            }
        }
    }

}