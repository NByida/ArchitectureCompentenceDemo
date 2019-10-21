package com.gmail.xuyimin1994. architecturecompentencedemo.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry
import com.gmail.xuyimin1994.architecturecompentencedemo.ui.baseUi.BaseActivity
import kotlinx.android.synthetic.main.activity_potery_detail.*
import kotlinx.android.synthetic.main.activity_potery_detail.context
import kotlinx.android.synthetic.main.activity_potery_detail.tv_title
import androidx.core.app.ActivityOptionsCompat




class PoetryDetailActivity:BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_potery_detail
    }

    lateinit var poetry:Poetry
    companion object {
        fun startMe(context: Activity, poetry:Poetry){
            context.startActivity(Intent(context,PoetryDetailActivity::class.java).putExtra("poetry",poetry))
        }

        fun startMe(context: Activity, poetry:Poetry,activityOptionsCompat:ActivityOptionsCompat){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(Intent(context,PoetryDetailActivity::class.java).putExtra("poetry",poetry),activityOptionsCompat.toBundle())
            }else{
                context.startActivity(Intent(context,PoetryDetailActivity::class.java).putExtra("poetry",poetry))
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        setClick()
    }

    fun setClick(){
        if(poetry.appreciation!!.length<5){
            lay_apprecate.visibility=GONE
        }
        if(poetry.notes!!.length<5){
            lay_notes.visibility=GONE
        }
        if(poetry.translate!!.length<5){
            lay_trans.visibility=GONE
        }
        lay_content.setOnClickListener {context.text=Html.fromHtml(poetry.content?.replace("</p> <p>","<br>"))  }
        lay_apprecate.setOnClickListener { context.text=Html.fromHtml(poetry.appreciation?.replace("</p> <p>","<br>"))  }
        lay_notes.setOnClickListener {context.text=Html.fromHtml(poetry.notes?.replace("</p> <p>","<br>"))  }
        lay_trans.setOnClickListener {context.text=Html.fromHtml(poetry.translate?.replace("</p> <p>","<br>"))  }
    }
}