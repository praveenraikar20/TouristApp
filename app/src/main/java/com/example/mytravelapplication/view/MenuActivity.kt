package com.example.mytravelapplication.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytravelapplication.R
import kotlinx.android.synthetic.main.activity_main.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_btn.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }
    }
}