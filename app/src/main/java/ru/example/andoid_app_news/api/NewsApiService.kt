package ru.example.andoid_app_news.api

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET

interface NewsApiService {

    @GET("https://lenta.ru/rss/last24")
    suspend fun getLentaNews() : ResponseBody

    @GET("http://static.feed.rbc.ru/rbc/logical/footer/news.rss")
    suspend fun getRbcNews() : ResponseBody

    @GET("https://3dnews.ru/breaking/rss/")
    suspend fun getTechNews() : ResponseBody

    @GET("https://nplus1.ru/rss")
    suspend fun getNplusNews() : ResponseBody

    companion object {
        fun instance(retrofit: Retrofit): NewsApiService  {
            return retrofit.create(NewsApiService::class.java)
        }
    }
}