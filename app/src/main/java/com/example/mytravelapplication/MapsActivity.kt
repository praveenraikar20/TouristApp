package com.example.mytravelapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

private const val TAG = "MapsActivity"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val directionList : MutableList<LatLng> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        directionList.add(LatLng(52.50930, 13.42937))
        directionList.add(LatLng(52.50904, 13.42913))
        directionList.add(LatLng(52.50895, 13.42904))
        directionList.add(LatLng(52.50868, 13.42880))

//        val intent = intent
//        val lat = intent.getDoubleExtra("lat", 0.0)
//        val long = intent.getDoubleExtra("long", 0.0)
//        val placeName = intent.getStringExtra("place name")
//        Log.i(TAG, "onMapReady: lat = $lat and long = $long")
//
//        val place = LatLng(lat, long)
//        map.addMarker(MarkerOptions().position(place).title(placeName))
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 10f))

        val line : Polyline = map.addPolyline(PolylineOptions()
                .addAll(directionList)
                .width(50f)
                .color(Color.RED))

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(52.50930, 13.42937), 15f))

    }
}