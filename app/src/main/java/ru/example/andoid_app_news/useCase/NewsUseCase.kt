package ru.example.andoid_app_news.useCase

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.service.rss.LentaRssParser
import ru.example.andoid_app_news.service.rss.NplusOneRssParser
import ru.example.andoid_app_news.service.rss.RbcRssParser
import ru.example.andoid_app_news.service.rss.TechRssParser

class NewsUseCase(
    private val newsRepo: NewsRepo,
    private val sharedPref: SharedPreferences,
) {

    fun getLentaNews(): Flow<List<News>> = flow {
        emit(newsRepo.loadLentaNews())
    }
        .flowOn(Dispatchers.IO)
        .map { list: ResponseBody ->
            parseLenta(list)
        }
        .catch {
            emptyList<News>()
        }
        .flowOn(Dispatchers.Default)

    fun getRbcNews(): Flow<List<News>> = flow {
        emit(newsRepo.loadRbcNews())
    }
        .flowOn(Dispatchers.IO)
        .map { list: ResponseBody ->
            Log.i("Loading", "Parsing rbc news...")
            parseRbc(list)
        }
        .catch {
            emptyList<News>()
            Log.e("Loading_Error", it.localizedMessage, it)
        }
        .flowOn(Dispatchers.Default)

    fun getTechNews(): Flow<List<News>> = flow {
        emit(newsRepo.loadTechNews())
    }
        .flowOn(Dispatchers.IO)
        .map { list: ResponseBody ->
            Log.i("Loading", "Parsing rbc news...")
            parseTech(list)
        }
        .catch {
            emptyList<News>()
            Log.e("Loading_Error", it.localizedMessage, it)
        }
        .flowOn(Dispatchers.Default)

    fun getNplusNews(): Flow<List<News>> = flow {
        emit(newsRepo.loadNplusNews())
    }
        .flowOn(Dispatchers.IO)
        .map { list: ResponseBody ->
            Log.i("Loading", "Parsing nplus news...")
            parseNplus(list)
        }
        .catch {
            emptyList<News>()
            Log.e("Loading_Error", it.localizedMessage, it)
        }
        .flowOn(Dispatchers.Default)

    fun getAllNews(): Flow<List<News>> =
        combine(
            getLentaNews(),
            getRbcNews(),
            getTechNews(),
            getNplusNews()
        ) { list1, list2, list3, list4 ->
            val newsResult: ArrayList<News> = ArrayList()
            newsResult.addAll(list1)
            newsResult.addAll(list2)
            newsResult.addAll(list3)
            newsResult.addAll(list4)
            newsResult.sortByDescending { el -> el.sourceDate }
            return@combine newsResult
        }.flowOn(Dispatchers.Default)

    private fun parseLenta(responseBody: ResponseBody): List<News>  {
        return try {
            val parser = LentaRssParser()
            Log.v("Context1", "Lenta parsing...  " + Thread.currentThread().name)
            parser.parse(responseBody.byteStream()).items ?: emptyList()
        } catch (t: Throwable) {
            emptyList()
        }
    }

    private fun parseRbc(responseBody: ResponseBody) : List<News> {
        return try {
            val parser = RbcRssParser()
            Log.v("Context1", "Rbc parsing...  " + Thread.currentThread().name)
            parser.parse(responseBody.byteStream()).items ?: emptyList()
        } catch (t: Throwable) {
            emptyList()
        }
    }

    private fun parseTech(responseBody: ResponseBody) : List<News> {
        return try {
            val parser = TechRssParser()
            Log.v("Context1", "3dnews parsing...  " + Thread.currentThread().name)
            parser.parse(responseBody.byteStream()).items ?: emptyList()
        } catch (t: Throwable) {
            emptyList()
        }
    }

    private fun parseNplus(responseBody: ResponseBody) : List<News> {
        return try {
            val parser = NplusOneRssParser()
            Log.v("Context1", "Nplus1 parsing...  " + Thread.currentThread().name)
            parser.parse(responseBody.byteStream()).items ?: emptyList()
        } catch (t: Throwable) {
            emptyList()
        }
    }

}