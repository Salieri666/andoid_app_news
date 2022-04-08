package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.service.BookmarksService

class BookmarksViewModel(private val bookmarksService: BookmarksService) : ViewModel() {

    val allBookmarks: LiveData<List<News>> = bookmarksService.getAll().asLiveData()

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