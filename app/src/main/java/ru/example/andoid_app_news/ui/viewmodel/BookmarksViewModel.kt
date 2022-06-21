package ru.example.andoid_app_news.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.useCase.BookmarksUseCase

class BookmarksViewModel(private val bookmarksUseCase: BookmarksUseCase) : ViewModel() {

    val allBookmarks: LiveData<List<News>> = bookmarksUseCase.getAll().asLiveData()

}

class BookmarksViewModelFactory(private val bookmarksUseCase: BookmarksUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarksViewModel(bookmarksUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}