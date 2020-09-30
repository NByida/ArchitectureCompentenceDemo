package com.gmail.xuyimin1994.architecturecompentencedemo.widget

import android.annotation.SuppressLint
import android.app.Service
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.LabelWrap
import com.gmail.xuyimin1994.architecturecompentencedemo.net.PoertyNet
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.MingJuActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.utils.ThreadMangager
import com.gmail.xuyimin1994.architecturecompentencedemo.widget.NewAppWidget.Companion.remoteViews
import com.umeng.commonsdk.statistics.common.MLog
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    init{
        appWidget=this
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        mcontext=context
        mappWidgetManager=appWidgetManager
        mappWidgetId=appWidgetIds
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        var mcontext: Context ?=null
        var mappWidgetManager: AppWidgetManager?=null
        var mappWidgetId: IntArray?=null
        var appWidget:NewAppWidget?=null
        var remoteViews:RemoteViews?=null
    }
}

var mingjuList= ArrayList<String>()



@SuppressLint("CheckResult")
internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    val widgetText = context.getString(R.string.app_name)
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
    remoteViews=views
    views.setTextViewText(R.id.appwidget_text, widgetText)
    getLabel()
    dispose?.dispose()
    dispose=Observable.interval(3*1000, TimeUnit.MILLISECONDS)
            .subscribe({
                if(mingjuList.size!=0){
                        views.setTextViewText(R.id.appwidget_text, mingjuList.get(it.toInt()%mingjuList.size))
                        Log.d("updateAppWidget",mingjuList.get(it.toInt()%mingjuList.size)+"current:"+it.toInt()+" index:"+it.toInt()%mingjuList.size)
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            },{
                Log.e("updateAppWidget",it.message)
            })
    appWidgetManager.updateAppWidget(appWidgetId, views)
    WeidgetService.updateAppWidgetIds(arrayOf(appWidgetId).toIntArray())
    if(!serviceStart){
        val intent=Intent(context,WeidgetService::class.java)
        startForegroundService(context,intent)
        serviceStart=true
    }
}


var serviceStart=false
var page=1
var dispose:Disposable?=null

fun getLabel(){
    launch({
        var value= PoertyNet.getInstance().getMingJu(page ++)
        mingjuList= ArrayList()
        value.list?.forEach {
            var word=it.words
                    .replace("。","")
                    .replace("，","\n")
                    .replace(",","\n")
            mingjuList.add(word)
        }
    },{
        Log.e("updateAppWidget",it.toString())
    })}

fun launch(block: suspend () -> Unit, error: (Throwable) -> Unit) = MainScope().launch {
    try {
        block()
    } catch (e: Throwable) {
        error.invoke(e)
    }
}