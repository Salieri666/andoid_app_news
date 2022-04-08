package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.service.BookmarksService

class CurrentNewsViewModel(private val bookmarksService: BookmarksService) : ViewModel() {

    fun getNewsByUrl(url: String): LiveData<News> {
        return bookmarksService.getByUrl(url).asLiveData()
    }

    fun add(news: News) = viewModelScope.launch {
        bookmarksService.add(news)
    }

    fun remove(id: Int) = viewModelScope.launch {
        bookmarksService.remove(id)
    }
}

class CurrentNewsViewModelFactory(private val bookmarksService: BookmarksService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentNewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrentNewsViewModel(bookmarksService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}