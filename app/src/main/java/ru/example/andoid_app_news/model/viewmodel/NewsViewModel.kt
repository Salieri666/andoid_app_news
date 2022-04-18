package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.repository.NewsRepo

class NewsViewModel(private val newsRepo: NewsRepo, private val source: String) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _newsList : MutableLiveData<List<News>> by lazy {
        newsRepo.getNewsBySource(source, _isLoading)
    }


    val newsList: LiveData<List<News>>
        get() = _newsList

}

class NewsViewModelFactory(private val newsRepo: NewsRepo, private val source: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsRepo, source) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}