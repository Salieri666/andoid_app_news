package ru.example.andoid_app_news.ui.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.useCase.BookmarksUseCase

class CurrentNewsViewModel(private val bookmarksUseCase: BookmarksUseCase) : ViewModel() {

    fun getNewsByUrl(url: String): LiveData<News> {
        return bookmarksUseCase.getByUrl(url).asLiveData()
    }

    fun add(news: News) = viewModelScope.launch {
        bookmarksUseCase.add(news)
    }

    fun remove(id: Int) = viewModelScope.launch {
        bookmarksUseCase.remove(id)
    }
}

class CurrentNewsViewModelFactory(private val bookmarksUseCase: BookmarksUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentNewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrentNewsViewModel(bookmarksUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}