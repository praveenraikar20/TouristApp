package com.example.mytravelapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytravelapplication.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val KEY_LATITUDE = "KEY_LATITUDE"
private const val KEY_LONGITUDE = "KEY_LONGITUDE"
private const val KEY_PLACE_NAME = "KEY_PLACE_NAME"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.clear()
        val searchIntent = intent
        val place = LatLng(
                searchIntent.getDoubleExtra(KEY_LATITUDE, 0.0),
                searchIntent.getDoubleExtra(KEY_LONGITUDE, 0.0)
        )
        map.addMarker(MarkerOptions().position(place).title(searchIntent.getStringExtra(KEY_PLACE_NAME)))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 10f))
    }
}