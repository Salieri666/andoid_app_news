package ru.example.andoid_app_news.ui.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.useCase.BookmarksUseCase
import javax.inject.Inject

@HiltViewModel
class CurrentNewsViewModel @Inject constructor(private val bookmarksUseCase: BookmarksUseCase) : BaseViewModel() {

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
