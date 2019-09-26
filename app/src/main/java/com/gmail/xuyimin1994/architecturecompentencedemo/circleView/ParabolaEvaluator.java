package com.gmail.xuyimin1994.architecturecompentencedemo.circleView;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * com.gmail.xuyimin1994.architecturecompentencedemo.circleView
 * yida
 * 2019/9/26 0026
 **/
public class ParabolaEvaluator implements TypeEvaluator<PointF> {
    PointF point;

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float timeLeft = fraction;
        point = new PointF();//结果
        float vx= 1000*endValue.x;
        //(float) (100*(random.nextFloat()-0.5));
        float vy=2500*endValue.y+3500;
        //1600*random.nextFloat()+1200;
        point.x=vx*timeLeft+startValue.x;
        point.y= (float) (startValue.y-((0.5*timeLeft)*vy-0.5*5000*timeLeft*timeLeft));
        return point;
    }

    public void destory(){
        if(point==null)return;
//        point=null;
    }
}
