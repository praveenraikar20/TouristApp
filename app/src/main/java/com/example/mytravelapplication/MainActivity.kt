package com.example.mytravelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_btn.setOnClickListener { startActivity(Intent(this, MapsActivity::class.java)) }
        current_location_btn.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java)) }
    }
}