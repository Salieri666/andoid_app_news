package ru.example.andoid_app_news.model.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.useCase.NewsUseCase

class NewsViewModel(private val newsUseCase: NewsUseCase,
                    private val source: String) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _news: MutableLiveData<List<News>> = MutableLiveData(emptyList())

    val news: LiveData<List<News>>
        get() = _news


    private suspend fun change(source: String) : List<News> {
        return when(source) {
            "All" -> newsUseCase.getAllNews()
            "Lenta" -> newsUseCase.getLentaNews()
            "Rbc" -> newsUseCase.getRbcNews()
            "3dnews" -> newsUseCase.getTechNews()
            else -> newsUseCase.getAllNews()
        }
    }

    fun loadNews() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            _news.postValue(change(source))
            _isLoading.postValue(false)
        }
    }

}

class NewsViewModelFactory(private val newsUseCase: NewsUseCase, private val source: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsUseCase, source) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}