package ru.example.andoid_app_news.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.flow.flow
import ru.example.andoid_app_news.MainApplication
import ru.example.andoid_app_news.api.ProfileApiService
import ru.example.andoid_app_news.model.data.AuthDto
import ru.example.andoid_app_news.model.data.ResponseInfo

class ProfileViewModel(
    application: Application,
    private val profileApiService: ProfileApiService
    ) : AndroidViewModel(application) {

    val isLogin : MutableLiveData<Boolean> = MutableLiveData(true)
    val isRegister : MutableLiveData<Boolean> = MutableLiveData(false)
    val isProfile : MutableLiveData<Boolean> = MutableLiveData(false)
    val login : MutableLiveData<String> = MutableLiveData()

    init {
        val preference = getApplication<MainApplication>().getSharedPreferences(
            "APP_PREFERENCES",
            Context.MODE_PRIVATE
        )

        if (preference.getBoolean("is_auth", false)) {
            isProfile.postValue(true)
            login.postValue(preference.getString("login", ""))
        }
    }


    fun authUser(authDto: AuthDto, isReg: Boolean) = flow {
        val response = if (isReg) profileApiService.registration(authDto) else profileApiService.login(authDto)
        if (response.isSuccessful) {
            val body : ResponseInfo? = response.body()
            body?.let {
                val preference = getApplication<MainApplication>().getSharedPreferences(
                    "APP_PREFERENCES",
                    Context.MODE_PRIVATE
                ).edit()
                preference.putString("token", it.token)
                preference.putString("login", it.username)
                preference.putBoolean("is_auth", true)
                preference.apply()

                login.postValue(it.username!!)
                it.isSuccess = true
            }
            isProfile.postValue(!isProfile.value!!)
            emit(body)
        } else {
            emit(ResponseInfo(null,null,null,false))
        }
    }.asLiveData()

    fun unLogin() {
        val preference = getApplication<MainApplication>().getSharedPreferences(
            "APP_PREFERENCES",
            Context.MODE_PRIVATE
        )

        if (preference.getBoolean("is_auth", false)) {
            val editor = preference.edit()
            editor.remove("login")
            editor.remove("token")
            editor.remove("is_auth")
            editor.apply()
        }

        isProfile.postValue(!isProfile.value!!)
    }
}

class ProfileViewModelFactory(private val profileApiService: ProfileApiService, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(application, profileApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}