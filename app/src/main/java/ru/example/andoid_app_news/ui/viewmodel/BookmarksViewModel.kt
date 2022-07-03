package ru.example.andoid_app_news.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.useCase.BookmarksUseCase
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(private var bookmarksUseCase: BookmarksUseCase) : BaseViewModel() {

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
