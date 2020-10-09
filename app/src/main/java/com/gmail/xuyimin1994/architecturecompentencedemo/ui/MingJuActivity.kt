package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.SnapHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.MingjuAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.adapter.PoetryAdapter
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.BaseBean
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.WordWrap
import com.gmail.xuyimin1994.architecturecompentencedemo.event.ChangeColor
import com.gmail.xuyimin1994.architecturecompentencedemo.event.GetWord
import com.gmail.xuyimin1994.architecturecompentencedemo.event.Search
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.RvActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.dialog.ChooseWordsDialog
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.dialog.ColorDialog
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.search.SearchActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.*
import com.gmail.xuyimin1994.architecturecompentencedemo.viewModel.MingJuViewModel
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.umeng.commonsdk.statistics.common.MLog
import kotlinx.android.synthetic.main.activity_mingju.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.item_poetry.*


class MingJuActivity: RvActivity() {

    companion object {
        fun startMe(context: Activity){
            context.startActivity(Intent(context,MingJuActivity::class.java))
        }
    }

    lateinit var viewModel: MingJuViewModel
    lateinit var observer: Observer<WordWrap>
    lateinit var adapter:MingjuAdapter

    var type: String=""

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun default(event: GetWord) {
        type=event.label
        page=1
        pullData(page)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun change(event: ChangeColor) {
      when(event.type){
          1->{
              ivImage.setImageDrawable(ColorDrawable(ContextCompat.getColor(this ,ColorUtlis.getIdByName(event.color))))
          }
          2->{
              adapter.data.forEach {
                  it.color=event.color
              }
              adapter.notifyDataSetChanged()
          }
      }
    }





    override fun pullData(page: Int) {
        viewModel.getMingJu(page,type)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mingju
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MingJuViewModel::class.java)
        initObserver()
        parentCreated()
        initRv()
        pullData( page++)
        refreshLayout.setEnableLoadMore(false)
        refreshLayout.setEnableRefresh(false)
        btChange.setOnClickListener {
            var dialog:ChooseWordsDialog?=null
            if(dialog==null){
                dialog=ChooseWordsDialog()

            }
            dialog?.show(supportFragmentManager)
        }
        btChangePic.setOnClickListener {
            //test
            SharePicActivity.startMe(context = this,word = adapter.data.get(manager!!.findFirstVisibleItemPosition()))
            return@setOnClickListener
            var  rxPermissions =  RxPermissions(this@MingJuActivity)
            rxPermissions.request(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            openpic()
                        } else {
                            ToastUtil.toastUtil.Short(this,"请授予权限").show()
                        }
                    }
        }
        save.setOnClickListener {
            savePicture(layparent)
        }
        color.setOnClickListener {54
            var colorDialog=ColorDialog.newDialog(1)
            colorDialog.show(supportFragmentManager)
        }
        textColor.setOnClickListener {
            var colorDialog=ColorDialog.newDialog(2)
            colorDialog.show(supportFragmentManager)
        }
        var visiable=true
        adapter.setOnItemClickListener { adapter, view, position ->
            if(visiable){
                layOperate.visibility= View.GONE
            }else layOperate.visibility= View.VISIBLE
            visiable=!visiable
        }
//        Debug.stopMethodTracing()

    }

    var pullTime=0L
    var manager:LinearLayoutManager?=null
    fun initRv(){
        manager= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv_auto.layoutManager=manager
        adapter= MingjuAdapter()
        var snapHelper=PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv_auto)
        adapter.bindToRecyclerView(rv_auto)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rv_auto.addOnScrollListener( object:OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(newState==SCROLL_STATE_IDLE&&(adapter.getData().size - manager!!.findFirstVisibleItemPosition() <= 3)){
                        if(System.currentTimeMillis()-pullTime>3000){
                            pullData(++page)
                            pullTime=System.currentTimeMillis()
                        }
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
    }

    fun initObserver(){
        observer= Observer {bean: WordWrap ->
            if(bean.statue==-1){
                if(page==1)refreshLayout.finishRefresh()else refreshLayout.finishLoadMore()
                ToastUtil.showToast(this,bean.msg)
                return@Observer
            }
            if(page==1){
                refreshLayout.finishRefresh()
                adapter.replaceData(bean.list!!)
            }else{
                refreshLayout.finishLoadMore()
                adapter.addData(bean.list!!)

            }
            adapter.notifyDataSetChanged()
        }
        viewModel.mingJu.observe(this,observer)
    }

    fun openpic(){
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
                .imageEngine(GlideEngine.createGlideEngine())
                .forResult(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: List<LocalMedia>) {
                        // onResult Callback
                        result?.get(0)?.let {
//                            rv_auto.background=BitmapDrawable(ImageUtil.getImageBitmap(it.realPath, DisplayUtils.getScreenWidth(this@MingJuActivity).toFloat(),DisplayUtils.getScreenHeight(this@MingJuActivity).toFloat()))
                            ivImage.setImageDrawable(BitmapDrawable(ImageUtil.getImageBitmap(it.realPath, DisplayUtils.getScreenWidth(this@MingJuActivity).toFloat(),DisplayUtils.getScreenHeight(this@MingJuActivity).toFloat())))
                        }
                    }

                    override fun onCancel() {
                        // onCancel Callback
                    }
                })
    }

    private fun savePicture(imPost:ViewGroup) {
        layOperate.visibility=View.INVISIBLE
        try {
            imPost.buildDrawingCache()  //启用DrawingCache并创建位图
            val bitmap = Bitmap.createBitmap(imPost.getDrawingCache()) //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
            val rxPermissions = RxPermissions(this)
            rxPermissions.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe { granted ->
                        if (granted) {
                            ImageUtil.saveBitmap2File(bitmap, this, "shareImage"+System.currentTimeMillis(), true)
                        } else {
                            ToastUtil.toastUtil.Short(this,"请授予权限")
                        }
                        layOperate.visibility=View.VISIBLE
                    }
            imPost.setDrawingCacheEnabled(false)
        } catch (e: Exception) {
            Log.e("",e.message)
            layOperate.visibility=View.VISIBLE
        }
    }


}