package com.example.mytravelapplication

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.provider.Settings



private const val REQUEST_LOCATION = 1
class CurrentLocationActivity : AppCompatActivity() {

    lateinit var btnGetLocation: Button
    lateinit var showLocation: TextView
    lateinit var locationManager: LocationManager
    lateinit var latitude: String
    lateinit var longitude: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_location)

        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)

        btnGetLocation.setOnClickListener {
            val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                onGPS()
            } else {
                getLocation()
            }
        }
    }

    private fun onGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes",  { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}