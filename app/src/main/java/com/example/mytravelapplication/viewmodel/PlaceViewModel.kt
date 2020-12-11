package com.example.mytravelapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mytravelapplication.model.PlaceRepository
import com.google.android.gms.maps.model.LatLng

class PlaceViewModel : ViewModel(){
    private val placeRepository = PlaceRepository()
    fun getPlaceCoordinates(placeName : String) : LiveData<LatLng> {
        placeRepository.getPlaceCoordinates(placeName)
        return placeRepository.getPlaceCoordinates(placeName)
    }
}