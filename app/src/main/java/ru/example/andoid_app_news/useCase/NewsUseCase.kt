package ru.example.andoid_app_news.useCase

import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.service.rss.*

class NewsUseCase(
    private val newsRepo: NewsRepo,
    private val sharedPref: SharedPreferences,
) {

    fun getAllNews(): Flow<List<News>> =
        combine(
            getNews(NewsSources.LENTA),
            getNews(NewsSources.RBC),
            getNews(NewsSources.TECH_NEWS),
            getNews(NewsSources.NPLUS1)
        ) { list1, list2, list3, list4 ->
            val newsResult: ArrayList<News> = ArrayList()
            newsResult.addAll(list1)
            newsResult.addAll(list2)
            newsResult.addAll(list3)
            newsResult.addAll(list4)
            newsResult.sortByDescending { el -> el.sourceDate }
            return@combine newsResult
        }.flowOn(Dispatchers.Default)


    private suspend fun getNewsBySourceType(type: NewsSources) : ResponseBody {
        return when (type) {
            NewsSources.LENTA -> newsRepo.loadLentaNews()
            NewsSources.RBC -> newsRepo.loadRbcNews()
            NewsSources.TECH_NEWS -> newsRepo.loadTechNews()
            NewsSources.NPLUS1 -> newsRepo.loadNplusNews()
            else -> throw IllegalArgumentException("The type -> $type not founded!")
        }
    }

    private fun getParserBySourceType(type: NewsSources) : AbstractRssParser {
        return when (type) {
            NewsSources.LENTA -> LentaRssParser()
            NewsSources.RBC -> RbcRssParser()
            NewsSources.TECH_NEWS -> TechRssParser()
            NewsSources.NPLUS1 -> NplusOneRssParser()
            else -> throw IllegalArgumentException("The type -> $type for parser not founded!")
        }
    }

    private fun parseNews(responseBody: ResponseBody, type: NewsSources) : List<News> {
        return try {
            val parser = getParserBySourceType(type)
            parser.parse(responseBody.byteStream()).items ?: emptyList()
        } catch (t: Throwable) {
            Log.e("Error_NEWS_APP", t.localizedMessage, t)
            emptyList()
        }
    }

    fun getNews(type: NewsSources): Flow<List<News>> = flow {
        emit(getNewsBySourceType(type))
    }
        .flowOn(Dispatchers.IO)
        .map { list: ResponseBody ->
            parseNews(list, type)
        }
        .catch {
            emptyList<News>()
            Log.e("Error_NEWS_APP", it.localizedMessage, it)
        }
        .flowOn(Dispatchers.Default)
}