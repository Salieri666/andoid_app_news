package ru.example.andoid_app_news.useCase

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.service.rss.LentaRssParser
import ru.example.andoid_app_news.service.rss.RbcRssParser
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
        return withContext(Dispatchers.IO) {
            val newsResult: ArrayList<News> = ArrayList()

            val lentaNews = async {
                val response = newsRepo.loadLentaNews()
                parseLenta(response)
            }

            newsResult.addAll(lentaNews.await())
            return@withContext newsResult
        }
    }

    suspend fun getRbcNews(): List<News> {
        return withContext(Dispatchers.IO) {
            val newsResult: ArrayList<News> = ArrayList()

            val rbcNews = async {
                val response = newsRepo.loadRbcNews()
                parseRbc(response)
            }

            newsResult.addAll(rbcNews.await())
            return@withContext newsResult
        }
    }


    private suspend fun parseLenta(responseBody: ResponseBody) : List<News> {
        return suspendCoroutine { continuation ->
            thread {
                try {
                    val parser = LentaRssParser()
                    val res = parser.parse(responseBody.byteStream()).items ?: emptyList()
                    continuation.resume(res)
                } catch (t: Throwable) {
                    continuation.resumeWithException(t)
                }
            }
        }
    }

    private suspend fun parseRbc(responseBody: ResponseBody) : List<News> {
        return suspendCoroutine { continuation ->
            thread {
                try {
                    val parser = RbcRssParser()
                    val res = parser.parse(responseBody.byteStream()).items ?: emptyList()
                    continuation.resume(res)
                } catch (t: Throwable) {
                    continuation.resumeWithException(t)
                }
            }
        }
    }

}