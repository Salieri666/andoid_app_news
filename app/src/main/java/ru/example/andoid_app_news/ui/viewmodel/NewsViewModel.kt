package ru.example.andoid_app_news.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.*
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.model.data.ResultData
import ru.example.andoid_app_news.useCase.NewsUseCase

class NewsViewModel(private val newsUseCase: NewsUseCase) : BaseViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _isRefreshing: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _news = MutableStateFlow(emptyList<News>())

    val isLoading: LiveData<Boolean> = _isLoading
    val isRefreshing: LiveData<Boolean> = _isRefreshing
    val news: StateFlow<List<News>> = _news


    fun loadNews(source: NewsSources, isRefresh: Boolean) {
        if (isRefresh) {
            loadNewsToValue(_isRefreshing) {
                load(source)
            }
        } else {
            loadNewsToValue(_isLoading) {
                load(source)
            }
        }
    }

    fun loadNews(sources: List<NewsSources>, isRefresh: Boolean) {
        if (isRefresh) {
            loadNewsToValue(_isRefreshing) {
                newsUseCase.getAllNewsByList(sources)
            }
        } else {
            loadNewsToValue(_isLoading) {
                newsUseCase.getAllNewsByList(sources)
            }
        }
    }

    private fun loadNewsToValue(
        loadingState: MutableLiveData<Boolean>,
        loadFunc: () -> Flow<ResultData<List<News>>>
    ) {
        loadFunc()
            .onEach {
                when (it.status) {
                    ResultData.Status.LOADING -> loadingState.postValue(true)
                    ResultData.Status.FAILED -> loadingState.postValue(false)
                    ResultData.Status.SUCCESSES -> {
                        loadingState.postValue(false)
                        _news.value = it.value!!
                    }
                    else -> loadingState.postValue(false)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun load(source: NewsSources): Flow<ResultData<List<News>>> {
        return when (source) {
            NewsSources.LENTA -> newsUseCase.getNews(NewsSources.LENTA)
            NewsSources.RBC -> newsUseCase.getNews(NewsSources.RBC)
            NewsSources.TECH_NEWS -> newsUseCase.getNews(NewsSources.TECH_NEWS)
            NewsSources.NPLUS1 -> newsUseCase.getNews(NewsSources.NPLUS1)
            else -> emptyFlow()
        }
    }
}

class NewsViewModelFactory(private val newsUseCase: NewsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}