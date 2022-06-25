package ru.example.andoid_app_news.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import ru.example.andoid_app_news.api.NewsApiService
import ru.example.andoid_app_news.model.data.NewsSources

class NewsRepo(
    private val newsApiService: NewsApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private suspend fun loadLentaNews(): ResponseBody = newsApiService.getLentaNews()

    private suspend fun loadRbcNews(): ResponseBody = newsApiService.getRbcNews()

    private suspend fun loadTechNews(): ResponseBody = newsApiService.getTechNews()

    private suspend fun loadNplusNews(): ResponseBody = newsApiService.getNplusNews()

    suspend fun getNewsBySourceType(type: NewsSources) : ResponseBody = withContext(ioDispatcher) {
        return@withContext when (type) {
            NewsSources.LENTA -> loadLentaNews()
            NewsSources.RBC -> loadRbcNews()
            NewsSources.TECH_NEWS -> loadTechNews()
            NewsSources.NPLUS1 -> loadNplusNews()
            else -> throw IllegalArgumentException("The source type -> $type not founded!")
        }
    }
}