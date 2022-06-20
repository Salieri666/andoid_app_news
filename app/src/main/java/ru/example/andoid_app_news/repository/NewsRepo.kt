package ru.example.andoid_app_news.repository

import okhttp3.ResponseBody
import ru.example.andoid_app_news.api.NewsApiService

class NewsRepo(
    private val newsApiService: NewsApiService
) {

    suspend fun loadLentaNews(): ResponseBody = newsApiService.getLentaNews()

    suspend fun loadRbcNews(): ResponseBody = newsApiService.getRbcNews()

    suspend fun loadTechNews(): ResponseBody = newsApiService.getTechNews()

    suspend fun loadNplusNews(): ResponseBody = newsApiService.getNplusNews()

}