package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.service.BookmarksService

class BookmarksViewModel(private val bookmarksService: BookmarksService) : ViewModel() {
    val allBookmarks: LiveData<List<News>> = bookmarksService.getAll().asLiveData()

    fun add(news: News) = viewModelScope.launch {
        bookmarksService.add(news)
    }

    fun remove(news: News) = viewModelScope.launch {
        bookmarksService.remove(news)
    }
}

class BookmarksViewModelFactory(private val bookmarksService: BookmarksService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarksViewModel(bookmarksService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}