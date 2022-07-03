package ru.example.andoid_app_news.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Retrofit
import ru.example.andoid_app_news.api.NewsApiService
import ru.example.andoid_app_news.model.data.NewsSources
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepo @Inject constructor(
    retrofit: Retrofit
) {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val newsApiService: NewsApiService = retrofit.create(NewsApiService::class.java)

    suspend fun getNewsBySourceType(type: NewsSources) : ResponseBody = withContext(ioDispatcher) {
        return@withContext when (type) {
            NewsSources.LENTA -> newsApiService.getLentaNews()
            NewsSources.RBC -> newsApiService.getRbcNews()
            NewsSources.TECH_NEWS -> newsApiService.getTechNews()
            NewsSources.NPLUS1 -> newsApiService.getNplusNews()
            else -> throw IllegalArgumentException("The source type -> $type not founded!")
        }
    }
}