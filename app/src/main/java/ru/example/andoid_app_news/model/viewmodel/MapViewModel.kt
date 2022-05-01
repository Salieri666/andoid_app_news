package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MapViewModel : ViewModel() {
    val points: MutableLiveData<ArrayList<LatLng>> = MutableLiveData(arrayListOf())

    private val _watchRoute: MutableLiveData<Boolean> = MutableLiveData(false)

    val watchRoute: LiveData<Boolean>
        get() = _watchRoute

    fun cancelRoute() {
        points.postValue(arrayListOf())
        _watchRoute.postValue(true)
    }

    fun startRoute() {
        _watchRoute.postValue(false)
    }

    fun addPoints(point: LatLng) {
        val list = points.value
        list?.add(point)
        points.postValue(list!!)
    }
}