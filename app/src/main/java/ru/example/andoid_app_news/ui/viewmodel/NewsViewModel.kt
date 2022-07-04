package ru.example.andoid_app_news.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.useCase.NewsUseCase
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsUseCase: NewsUseCase) : BaseViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _news = MutableStateFlow(emptyList<News>())

    val isLoading: LiveData<Boolean> = _isLoading
    val isRefreshing: LiveData<Boolean> = _isRefreshing
    val news: StateFlow<List<News>> = _news


    fun loadNews(source: NewsSources, isRefresh: Boolean) {
        loadNewsToValue(if (isRefresh) _isRefreshing else _isLoading) {
            newsUseCase.getNews(source)
        }
    }

    fun loadNews(sources: List<NewsSources>, isRefresh: Boolean) {
        loadNewsToValue(if (isRefresh) _isRefreshing else _isLoading) {
            newsUseCase.getAllNewsByList(sources)
        }
    }

    private fun loadNewsToValue(
        loadingState: MutableLiveData<Boolean>,
        loadFunc: () -> Flow<List<News>>
    ) {
        loadFunc()
            .onStart {
                loadingState.postValue(true)
            }
            .onEach {
                _news.value = it
            }
            .catch {
                loadingState.postValue(false)
                Log.e("ERROR_TYPE", it.localizedMessage, it)
            }
            .onCompletion {
                loadingState.postValue(false)
            }
            .launchIn(viewModelScope)
    }
}
