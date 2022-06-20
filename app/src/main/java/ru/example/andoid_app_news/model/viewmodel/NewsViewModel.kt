package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.useCase.NewsUseCase

class NewsViewModel(private val newsUseCase: NewsUseCase) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _news = MutableStateFlow(emptyList<News>())
    val news: StateFlow<List<News>> = _news


    private fun load(source: NewsSources) : Flow<List<News>> {
        return when(source) {
            NewsSources.LENTA -> newsUseCase.getNews(NewsSources.LENTA)
            NewsSources.RBC -> newsUseCase.getNews(NewsSources.RBC)
            NewsSources.TECH_NEWS -> newsUseCase.getNews(NewsSources.TECH_NEWS)
            NewsSources.NPLUS1 -> newsUseCase.getNews(NewsSources.NPLUS1)
            else -> emptyFlow()
        }
    }

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

    private fun loadNewsToValue(load: () -> Flow<List<News>>) {
        load()
            .onEach {
                _news.value = it
                if (it.isNotEmpty())
                    _isLoading.postValue(false)
            }
            .launchIn(viewModelScope)
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