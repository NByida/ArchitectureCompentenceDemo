package com.gmail.xuyimin1994.architecturecompentencedemo.circleView;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Stack;

import androidx.annotation.RequiresApi;

/**
 * com.gmail.xuyimin1994.architecturecompentencedemo.circleView
 * yida
 * 2019/9/26 0026
 **/
public class PeriscopeLayout extends LinearLayout {

    private long time;
    private final String TAG="periscopelayout";
    public PeriscopeLayout(Context context) {
        super(context);

    }

    public PeriscopeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public PeriscopeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PeriscopeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    Stack<CircleView> cachViews=new Stack <>();

    private  void  addView(float x1,float y1){
        long timeNow=System.currentTimeMillis();
        if(timeNow-time<30)return;
        time=timeNow;
        final float[] x = new float[1];
        final float[] y = new float[1];
        x[0] =x1;
        y[0] =y1;
        CircleView view;
        if(cachViews.empty()){
            view=new CircleView(getContext());
        }else {
            Log.e("stack size",cachViews.size()+"");
            Log.e("stack size--",getChildCount()+"");
            view=cachViews.pop();
        }
        addView(view);
        view.startAnim(new PointF(x1,y1), v->{
            detach(v);
            cachViews.push((CircleView)v);
        });
    }

    private void detach(View view){
        for(int i=0;i<getChildCount();i++){
            if(view==getChildAt(i)){
                removeView(view);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                addView(event.getX(),event.getY());
                addView(event.getX(),event.getY());
                addView(event.getX(),event.getY());

                break;
            case MotionEvent.ACTION_MOVE:
                addView(event.getX(),event.getY());
                addView(event.getX(),event.getY());
                addView(event.getX(),event.getY());

                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;

        }
        return true;
    }
}

