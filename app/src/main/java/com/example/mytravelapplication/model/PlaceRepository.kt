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
    private val errorData = MutableLiveData<Throwable>()

    fun findPlaceCoordinates(placeName : String){
        val placeData = retrofit.getPlaceData(placeName)
        placeData.enqueue(object : Callback<SearchPlaceResponseData> {
            override fun onResponse(
                call: Call<SearchPlaceResponseData>,
                response: Response<SearchPlaceResponseData>
            ) {
                for (i in response.body()!!.results) {
                    Log.i(TAG, "onResponse: lat = ${i.position.lat} and long = ${i.position.lon}")
                    coordinates.value = LatLng(i.position.lat, i.position.lon)
                }
            }

            override fun onFailure(call: Call<SearchPlaceResponseData>, throwable: Throwable) {
                errorData.value = throwable
            }
        })
    }

    fun getCoordinates() : LiveData<LatLng> =   coordinates
}