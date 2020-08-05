package com.gmail.xuyimin1994.architecturecompentencedemo.entity

data class TagWrap(var size:String,var list:MutableList<Tag>?, var statue:Int,var msg:String){
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


data class Tag(var id:String,var tag:String)