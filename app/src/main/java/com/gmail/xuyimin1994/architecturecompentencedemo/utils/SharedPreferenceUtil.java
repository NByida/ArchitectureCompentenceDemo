package com.gmail.xuyimin1994.architecturecompentencedemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.umeng.commonsdk.statistics.common.MLog;

/**
 * com.gmail.xuyimin1994.architecturecompentencedemo.utils
 * yida
 * 2019/9/24 0024
 **/
public class SharedPreferenceUtil {
    private static final String FILE_NAME = "hello"; //文件名
    private static SharedPreferenceUtil mInstance;
    private static  Context context;

    private SharedPreferenceUtil(){}

    public static SharedPreferenceUtil getInstance(Context context){
        SharedPreferenceUtil.context= context;
        if(mInstance == null){
            synchronized (SharedPreferenceUtil.class){
                if(mInstance == null){
                    mInstance = new SharedPreferenceUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 存入键值对
     * @param key
     * @param value
     */

    public void put( String key, Object value){
        //判断类型
        String type = value.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if("Integer".equals(type)){
            editor.putInt(key,(Integer)value);
        }else if ("Boolean".equals(type)){
            editor.putBoolean(key,(Boolean)value);
        }else if ("Float".equals(type)){
            editor.putFloat(key,(Float)value);
        }else if ("Long".equals(type)){
            editor.putLong(key,(Long)value);
        }else if ("String".equals(type)){
            editor.putString(key,(String) value);
        }
        editor.apply();
    }

    /**
     * 读取键的值，若无则返回默认值
     * @param key
     * @param defValue 默认值
     * @return
     */
    @Nullable
    public Object get(String key, Object defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String type = defValue.getClass().getSimpleName();
        if("Integer".equals(type)){
            return sharedPreferences.getInt(key,(Integer)defValue);
        }else if ("Boolean".equals(type)){
            return sharedPreferences.getBoolean(key,(Boolean)defValue);
        }else if ("Float".equals(type)){
            return sharedPreferences.getFloat(key,(Float)defValue);
        }else if ("Long".equals(type)){
            return sharedPreferences.getLong(key,(Long)defValue);
        }else if ("String".equals(type)){
            return sharedPreferences.getString(key,(String) defValue);
        }
        return null;
    }

    public <T> void  putObject(String key,T obj){
        Gson gson=new Gson();
        put(key,gson.toJson(obj));
    }

    public <T> T getObject(String key, Class<T> tClass){
        Gson gson=new Gson();
        String result= (String) get(key,"");
        if(result==null)return null;
        T t=null;
        try {
            t= (T) gson.fromJson(result,tClass);
        }catch (Exception e){
            MLog.e(getClass().getSimpleName(),e.getMessage());
        }finally {
            return t;
        }
    }

}
