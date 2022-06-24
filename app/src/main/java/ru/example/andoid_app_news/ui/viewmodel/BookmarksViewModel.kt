package ru.example.andoid_app_news.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.useCase.BookmarksUseCase

class BookmarksViewModel(private var bookmarksUseCase: BookmarksUseCase) : BaseViewModel() {

    private val _allBookmarks = MutableStateFlow(emptyList<News>())

    val allBookmarks: StateFlow<List<News>> = _allBookmarks

    //TODO Paging
    fun loadBookmarks() {
        bookmarksUseCase.getAll()
            .onEach {
                _allBookmarks.value = it
            }
            .launchIn(viewModelScope)

    }
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