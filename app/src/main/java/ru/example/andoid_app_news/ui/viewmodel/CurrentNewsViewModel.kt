package ru.example.andoid_app_news.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.useCase.BookmarksUseCase

class CurrentNewsViewModel(private val bookmarksUseCase: BookmarksUseCase) : BaseViewModel() {

    private val _selectedNews = MutableStateFlow(News())

    val selectedNews : StateFlow<News> = _selectedNews

    fun getNewsByUrl(url: String) {
        bookmarksUseCase.getByUrl(url)
            .onEach {
                _selectedNews.value = it
            }
            .launchIn(viewModelScope)
    }

    //TODO remove value when deleting
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