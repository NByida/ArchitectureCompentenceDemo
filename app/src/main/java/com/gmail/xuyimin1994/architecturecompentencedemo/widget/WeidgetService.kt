package com.gmail.xuyimin1994.architecturecompentencedemo.widget

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit


class  WeidgetService: Service() {

    var dispose:Disposable?=null
    override fun onDestroy() {
        super.onDestroy()
        dispose?.dispose()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        dispose= Observable.interval(10*60*1000, TimeUnit.MILLISECONDS)
                .subscribe({
                    NewAppWidget.appWidget?.let {
                        it.onUpdate(context = NewAppWidget.mcontext!!, appWidgetManager = NewAppWidget.mappWidgetManager!!, appWidgetIds = NewAppWidget.mappWidgetId!!)
                    }
                },{
                    Log.e("WeidgetService",it.message)
                })
        createNotificationChannel()
    }

    companion object{
        private val sAppWidgetIds: Queue<Int> = LinkedList<Int>()
        private val sLock = Any()
        fun updateAppWidgetIds(appWidgetIds: IntArray) {
            synchronized(sLock) {
                for (appWidgetId in appWidgetIds) {
                    sAppWidgetIds.add(appWidgetId)
                }
            }
        }
    }

    private  fun createNotificationChannel() {
        val CHANNEL_ID = "com.gmail.xuyimin1994.architecturecompentencedemo.service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notification=Notification.Builder(this,CHANNEL_ID)
                    .setContentTitle("沓诗词").setContentText("诗句壁纸")
                    .setSmallIcon(R.drawable.list_selector_background)
                    .build()
            startForeground(1, notification)
        }
    }

}