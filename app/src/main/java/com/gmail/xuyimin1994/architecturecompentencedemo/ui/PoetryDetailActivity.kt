package com.gmail.xuyimin1994.architecturecompentencedemo.ui

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gmail.xuyimin1994.architecturecompentencedemo.R
import com.gmail.xuyimin1994.architecturecompentencedemo.entity.Poetry

class PoetryDetailActivity:AppCompatActivity() {

    companion object {
        fun startMe(context:AppCompatActivity,poetry:Poetry){
            context.startActivity(Intent(context,PoetryDetailActivity::class.java).putExtra("poetry",poetry))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_potery_detail)
        var poetry:Poetry=intent.getParcelableExtra("poetry")

        var title:TextView=findViewById(R.id.tv_title)
        var name:TextView=findViewById(R.id.tv_name)
        var context:TextView=findViewById(R.id.context)
        var fanyi:TextView=findViewById(R.id.fanyi)
        var zhushi:TextView=findViewById(R.id.zhushi)
        var pinyin:TextView=findViewById(R.id.pinyin)
        var shangxi:TextView=findViewById(R.id.shangxi)



        title.setText(Html.fromHtml(poetry.name?.replace("</p> <p>","<br>")))
        name.setText(Html.fromHtml(poetry.poet?.replace("</p> <p>","<br>")))
        context.setText(Html.fromHtml(poetry.content?.replace("</p> <p>","<br>")))
        fanyi.setText(Html.fromHtml(poetry.translate?.replace("</p> <p>","<br>")))
        zhushi.setText(Html.fromHtml(poetry.notes?.replace("</p> <p>","<br>")))
        pinyin.setText(Html.fromHtml(poetry.pinyin?.replace("</p> <p>","<br>")))
        shangxi.setText(Html.fromHtml(poetry.appreciation?.replace("</p> <p>","<br>")))



    }
}