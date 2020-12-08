package com.example.mytravelapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.mytravelapplication.R
import com.example.mytravelapplication.model.RetrofitInitializer
import com.example.mytravelapplication.model.RetrofitInterface
import com.example.mytravelapplication.viewmodel.PlaceViewModel
import kotlinx.android.synthetic.main.activity_search.*


private const val TAG = "SearchActivity"
class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel::class.java)

        search_btn.setOnClickListener {
            if (search_et.text.isEmpty()){
                Toast.makeText(this, "Search field empty !", Toast.LENGTH_SHORT).show()
            }else{
                val placeName = search_et.text.toString()
                placeViewModel.getPlaceCoordinates(placeName).observe(this, {
                    if (it == null) {
                        Log.i(TAG, "onCreate: some error occurred")
                    } else {
                        Log.i(TAG, "onCreate: Lat = ${it.latitude} and Long = ${it.longitude}")

                    }
                })
            }
        }
    }
}