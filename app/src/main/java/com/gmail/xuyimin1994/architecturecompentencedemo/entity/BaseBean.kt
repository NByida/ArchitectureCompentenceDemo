package com.gmail.xuyimin1994.architecturecompentencedemo.entity

/**
 *com.gmail.xuyimin1994.architecturecompentencedemo.entity
 *yida
 *2019/9/17 0017
 **/
class BaseBean(var size:String,var list:List<Poetry>?, var statue:Int,var msg:String) {
    init {
        this.statue=0
        this.msg=""
        this.list=null
        this.size=""
    }
    constructor(code:Int) : this("",null,-1,""){
        statue=code
    }
}