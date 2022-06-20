package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.model.data.ResultData
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.useCase.NewsUseCase

class NewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val _news = MutableStateFlow(emptyList<News>())

    val isLoading: LiveData<Boolean> = _isLoading
    val news: StateFlow<List<News>> = _news


    fun loadNews(source: NewsSources) {
        loadNewsToValue {
            load(source)
        }
    }

    fun loadNews(sources: List<NewsSources>) {
        loadNewsToValue {
            newsUseCase.getAllNewsByList(sources)
        }
    }

    private fun loadNewsToValue(loadFunc: () -> Flow<ResultData<List<News>>>) {
        loadFunc()
            .onEach {
                when (it.status) {
                    ResultData.Status.LOADING -> _isLoading.postValue(true)
                    ResultData.Status.FAILED -> _isLoading.postValue(false)
                    ResultData.Status.SUCCESSES -> {
                        _isLoading.postValue(false)
                        _news.value = it.value!!
                    }
                    else -> _isLoading.postValue(false)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun load(source: NewsSources) : Flow<ResultData<List<News>>> {
        return when(source) {
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