package ru.example.andoid_app_news.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import okhttp3.*
import ru.example.andoid_app_news.model.data.Channel
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.service.rss.LentaRssParser
import java.io.IOException

class NewsRepo(private val client: OkHttpClient) {

    private fun getAllNews(): MutableLiveData<List<News>> {
        val news = MutableLiveData<List<News>>(emptyList())

        val request = Request.Builder()
            .url("https://lenta.ru/rss/news")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("API", "Error -> " + e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val rssParser = LentaRssParser()
                    val stream = response.body?.byteStream()
                    val result = if (stream != null) rssParser.parse(stream) else Channel()

                    result.items?.let {
                        news.postValue(it)
                    }
                }
            }
        })
        return news
    }

    fun getNewsBySource(source: String): MutableLiveData<List<News>> {
        return when (source) {
            "All" -> getAllNews()
            "Lenta" -> getAllNews()
            else -> MutableLiveData()
        }
    }
}