package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import kotlinx.android.synthetic.main.activity_potery_detail.*
import kotlinx.android.synthetic.main.activity_potery_detail.context
import kotlinx.android.synthetic.main.activity_potery_detail.tv_title


class PoetryDetailActivity:AppCompatActivity() {
    lateinit var poetry:Poetry
    companion object {
        fun startMe(context:AppCompatActivity,poetry:Poetry){
            context.startActivity(Intent(context,PoetryDetailActivity::class.java).putExtra("poetry",poetry))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potery_detail)
        poetry=intent.getParcelableExtra("poetry")
        tv_title.setText(Html.fromHtml(poetry.name?.replace("</p> <p>","<br>")))
        tv_potery_name.setText(Html.fromHtml(poetry.poet?.replace("</p> <p>","<br>")))

        var mycontext=poetry.content?.replace("</p> <p>","<br>")!!
               .replace("，<br>","，")
               .replace("，","，<br>")
               .replace("；","；<br>")
               .replace("？","，<br>")
               .replace("！","，<br>")
               .replace("。&nbsp","。")
               .replace("。&nbsp;","。")
               .replace("。&nbsp ;","。")
               .replace("。 <br/>","。")
               .replace("。 <br>","。")
               .replace("。<br/>","。")
               .replace("。<br>","。")
               .replace("。","。<br>")
               .replace("。<br>;","。<br>")
               .replace("<br> <br>","<br>")
               .replace("<br><br/>","<br>")
               .replace("<br><br>","<br>")
               .replace("<br></span><br/>","<br>")

        context.text=Html.fromHtml(mycontext)
        val typeFaceHold = Typeface.createFromAsset(getAssets(), "fonts/xing_kai.ttf")
        tv_title.setTypeface(typeFaceHold)
        tv_potery_name.setTypeface(typeFaceHold)
        context.setTypeface(typeFaceHold)
        fanyi.setTypeface(typeFaceHold)
        zhushi.setTypeface(typeFaceHold)
        pinyin.setTypeface(typeFaceHold)
        shangxi.setTypeface(typeFaceHold)
        setClick()
    }

    fun setClick(){
        lay_content.setOnClickListener {context.text=Html.fromHtml(poetry.content?.replace("</p> <p>","<br>"))  }
        lay_apprecate.setOnClickListener {context.text=Html.fromHtml(poetry.appreciation?.replace("</p> <p>","<br>"))  }
        lay_notes.setOnClickListener {context.text=Html.fromHtml(poetry.notes?.replace("</p> <p>","<br>"))  }
        lay_trans.setOnClickListener {context.text=Html.fromHtml(poetry.translate?.replace("</p> <p>","<br>"))  }
    }
}