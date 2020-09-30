package com.gmail.xuyimin1994.architecturecompentencedemo.entity

/**
 * 名句标签大类
 */
data class WordsLabel(var label_name:String,
                       var id:String
)

/**
 * 名句标签小类
 */
data class WordSmallLable(var label_id:String,
                           var label_name:String,
                           var type:String,
                           var url:String
)

/**
 * 名句
 */
data class Word(var id:String,
                 var label_name:String,
                 var type:String,
                 var url:String,
                 var words:String,
                 var name:String,
                 var poet:String,
                 var linkId:String,
                 var color:String=""
)

data class Label( var label_name:String,var id:String)

data class LabelWrap(var size:String,var list:MutableList<Label>?, var statue:Int,var msg:String){
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

data class WordWrap(var size:String,var list:MutableList<Word>?, var statue:Int,var msg:String){
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


data class Type( var label_name:String
                 ,var label_id:String
                 ,var type:String
)

data class  Wrap<T>(var size:String,var list:MutableList<T>?, var statue:Int,var msg:String){
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

