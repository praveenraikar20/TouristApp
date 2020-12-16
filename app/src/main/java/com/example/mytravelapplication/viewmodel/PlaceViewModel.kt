package com.example.mytravelapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytravelapplication.model.PlaceRepository
import com.google.android.gms.maps.model.LatLng

class PlaceViewModel : ViewModel(){
    private val placeRepository = PlaceRepository()
    private var coordinates = MutableLiveData<LatLng>()
    fun findPlaceCoordinates(placeName : String) {
        coordinates = placeRepository.getPlaceCoordinates(placeName)
    }
    fun getCoordinates() : LiveData<LatLng> = coordinates
}