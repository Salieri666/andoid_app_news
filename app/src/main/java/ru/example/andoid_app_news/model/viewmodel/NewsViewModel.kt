package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.samples.NewsSamples

class NewsViewModel : ViewModel() {

    private val _newsList : MutableLiveData<List<News>> by lazy {
        MutableLiveData(NewsSamples.getNews())
    }

    val newsList: LiveData<List<News>>
        get() = _newsList

}