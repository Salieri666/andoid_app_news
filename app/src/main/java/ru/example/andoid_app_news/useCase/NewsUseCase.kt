package ru.example.andoid_app_news.useCase

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.service.rss.LentaRssParser
import ru.example.andoid_app_news.service.rss.RbcRssParser

class NewsUseCase(
    private val newsRepo: NewsRepo,
    private val sharedPref: SharedPreferences,
) {

    suspend fun getAllNews(): List<News> {
        return withContext(Dispatchers.IO) {
            val newsResult: ArrayList<News> = ArrayList()

            val lentaNews = launch {
                if (sharedPref.getBoolean("Lenta", true)) {
                    val response = newsRepo.loadLentaNews()
                    newsResult.addAll(parseLenta(response))
                }
            }

            val rbcNews = launch {
                if (sharedPref.getBoolean("Rbc", true)) {
                    val response = newsRepo.loadRbcNews()
                    newsResult.addAll(parseRbc(response))
                }
            }

            lentaNews.join()
            rbcNews.join()
            newsResult.sortByDescending { el -> el.sourceDate }
            return@withContext newsResult
        }
    }


    suspend fun getLentaNews(): List<News> {
        val response = newsRepo.loadLentaNews()
        return parseLenta(response)
    }

    suspend fun getRbcNews(): List<News> {
        val response = newsRepo.loadRbcNews()
        return parseRbc(response)
    }


    private fun parseLenta(responseBody: ResponseBody): List<News>  {
        return try {
            val parser = LentaRssParser()
            parser.parse(responseBody.byteStream()).items ?: emptyList()
        } catch (t: Throwable) {
            emptyList()
        }
    }

    private fun parseRbc(responseBody: ResponseBody) : List<News> {
        return try {
            val parser = RbcRssParser()
            parser.parse(responseBody.byteStream()).items ?: emptyList()
        } catch (t: Throwable) {
            emptyList()
        }
    }

}