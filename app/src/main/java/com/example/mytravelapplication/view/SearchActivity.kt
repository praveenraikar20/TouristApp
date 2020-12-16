package com.example.mytravelapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.mytravelapplication.R
import com.example.mytravelapplication.viewmodel.PlaceViewModel
import kotlinx.android.synthetic.main.activity_search.*

private val TAG = SearchActivity::class.java.simpleName
private const val KEY_LATITUDE = "KEY_LATITUDE"
private const val KEY_LONGITUDE = "KEY_LONGITUDE"
private const val KEY_PLACE_NAME = "KEY_PLACE_NAME"

class SearchActivity : AppCompatActivity() {

    private lateinit var placeName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel::class.java)

        btn_search.setOnClickListener {
            placeName = et_search.text.toString()
            if (placeName.isEmpty()) {
                Toast.makeText(this, "Search field empty !", Toast.LENGTH_SHORT).show()
            } else {
                placeViewModel.findPlaceCoordinates(placeName)
                placeViewModel.getCoordinates().observe(this, {
                    val mapsIntent = Intent(this, MapsActivity::class.java)
                    mapsIntent.putExtra(KEY_LATITUDE, it.latitude)
                    mapsIntent.putExtra(KEY_LONGITUDE, it.longitude)
                    mapsIntent.putExtra(KEY_PLACE_NAME, placeName)
                    startActivity(mapsIntent)
                })
            }
        }
    }
}