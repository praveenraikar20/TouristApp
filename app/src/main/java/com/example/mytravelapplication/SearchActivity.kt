package com.example.mytravelapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "SearchActivity"
class SearchActivity : AppCompatActivity() {

    private val retrofit = RetrofitInitializer.getInstance()
    private val apiInterface = retrofit.create(RetrofitInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_btn.setOnClickListener {
            if (search_et.text.isEmpty()){
                Toast.makeText(this, "Search field empty !", Toast.LENGTH_SHORT).show()
            }else{
                val placeName = search_et.text.toString()
                searchPlace(placeName)
            }
        }
    }

    private fun searchPlace(placeName : String){
        val placeResponseData: Call<ResponseData> = apiInterface.getPlaceData(placeName)
        val mapIntent = Intent(this, MapsActivity::class.java)
        placeResponseData.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                Log.i(TAG, "onResponse: Response received")
                mapIntent.putExtra("place name", response.body()!!.summary.query)
                for (i in response.body()!!.results) {
                    Log.i(TAG, "onResponse: lat = ${i.position.lat} and long = ${i.position.lon}")
                    mapIntent.putExtra("lat", i.position.lat)
                    mapIntent.putExtra("long", i.position.lon)
                    startActivity(mapIntent)
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }
        })
    }
}