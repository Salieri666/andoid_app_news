package ru.example.andoid_app_news.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import ru.example.andoid_app_news.api.NewsApiService

class NewsRepo(
    private val newsApiService: NewsApiService
) {

    suspend fun loadLentaNews(): ResponseBody = withContext(Dispatchers.IO) {
        Log.v("Context1", "Lenta loading...  " + Thread.currentThread().name)
        newsApiService.getLentaNews()
    }

    suspend fun loadRbcNews(): ResponseBody = withContext(Dispatchers.IO) {
        Log.v("Context1", "Rbc loading...  " + Thread.currentThread().name)
        newsApiService.getRbcNews()
    }

    suspend fun loadTechNews(): ResponseBody = withContext(Dispatchers.IO) {
        Log.v("Context1", "3DNews loading...  " + Thread.currentThread().name)
        newsApiService.getTechNews()
    }

}