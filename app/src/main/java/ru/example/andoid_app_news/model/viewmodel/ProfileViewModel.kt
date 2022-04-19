package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    val isLogin : MutableLiveData<Boolean> = MutableLiveData(true)
    val isRegister : MutableLiveData<Boolean> = MutableLiveData(false)
    val isProfile : MutableLiveData<Boolean> = MutableLiveData(false)

}