package com.example.mytravelapplication.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytravelapplication.model.data.SearchPlaceResponseData
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "SearchPlaceRepository"

class PlaceRepository {
    private val retrofit = RetrofitInitializer.getInstance().create(RetrofitInterface::class.java)
    private val coordinates = MutableLiveData<LatLng>()
    fun getPlaceCoordinates(placeName: String): LiveData<LatLng> {
        val placeData = retrofit.getPlaceData(placeName)
        placeData.enqueue(object : Callback<SearchPlaceResponseData> {
            override fun onResponse(
                    call: Call<SearchPlaceResponseData>,
                    response: Response<SearchPlaceResponseData>
            ) {
                for (result in response.body()?.results!!) {
                    Log.i(TAG, "onResponse: lat = ${result.position.lat} and long = ${result.position.lon}")
                    coordinates.value = LatLng(result.position.lat, result.position.lon)
                }
            }

            override fun onFailure(call: Call<SearchPlaceResponseData>, throwable: Throwable) {
                Log.d(TAG, "onFailure: ${throwable.message}")
            }
        })
        return coordinates
    }
}