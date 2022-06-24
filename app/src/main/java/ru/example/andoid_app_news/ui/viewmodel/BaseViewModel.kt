package ru.example.andoid_app_news.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {

    private var coroutineContext = SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { context, ex ->
        Log.e("Error", context.toString() + "  " + ex.message.toString())
    }

    protected val viewModelScope : CoroutineScope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}