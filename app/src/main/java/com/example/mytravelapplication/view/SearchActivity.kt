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
private const val EXTRA_LATITUDE = "EXTRA_LATITUDE"
private const val EXTRA_LONGITUDE = "EXTRA_LONGITUDE"
private const val EXTRA_PLACE_NAME = "EXTRA_PLACE_NAME"

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel::class.java)

        btn_search.setOnClickListener {
            if (et_search.text.isEmpty()) {
                Toast.makeText(this, "Search field empty !", Toast.LENGTH_SHORT).show()
            } else {
                val placeName = et_search.text.toString()
                placeViewModel.getPlaceCoordinates(placeName).observe(this, {
                    if (it == null) {
                        Log.i(TAG, "onCreate: some error occurred")
                    } else {
                        val mapsIntent = Intent(this, MapsActivity::class.java)
                        mapsIntent.putExtra(EXTRA_LATITUDE, it.latitude)
                        mapsIntent.putExtra(EXTRA_LONGITUDE, it.longitude)
                        mapsIntent.putExtra(EXTRA_PLACE_NAME, placeName)
                        startActivity(mapsIntent)
                    }
                })
            }
        }
    }
}