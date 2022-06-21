package ru.example.andoid_app_news.useCase

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import okhttp3.ResponseBody
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.model.data.ResultData
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.rss.*

class NewsUseCase(
    private val newsRepo: NewsRepo,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    fun getNews(type: NewsSources): Flow<ResultData<List<News>>> = flow {

        val response = getNewsBySourceType(type)
        val res = parseNews(response, type)
        emit(
            ResultData(ResultData.Status.SUCCESSES, res)
        )

    }.onStart {
        emit(ResultData(ResultData.Status.LOADING))
    }.catch {
        Log.e("Error_NEWS_APP", it.localizedMessage, it)
        ResultData(ResultData.Status.FAILED, emptyList<News>())
    }

    fun getAllNewsByList(list: List<NewsSources>): Flow<ResultData<List<News>>> = flow {

        val news = arrayListOf<News>()
        coroutineScope {

            list.mapIndexed  { _, source ->
                async {
                    news.addAll(parseNews(getNewsBySourceType(source), source))
                }
            }.awaitAll()

            withContext(defaultDispatcher) {
                news.sortByDescending { el -> el.sourceDate }
            }

        }
        emit(ResultData(ResultData.Status.SUCCESSES, news as List<News>))

    }.onStart {
        emit(ResultData(ResultData.Status.LOADING))
    }.catch {
        Log.e("Error_NEWS_APP", it.localizedMessage, it)
        ResultData(ResultData.Status.FAILED, emptyList<News>())
    }


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

    private suspend fun parseNews(responseBody: ResponseBody, type: NewsSources) : List<News> = withContext(defaultDispatcher) {
        val resultList = arrayListOf<News>()
        val parser = getParserBySourceType(type)
        kotlin.runCatching {
            Log.v("Context1", "Parsing...  " + Thread.currentThread().name)
            resultList.addAll(parser.parse(responseBody.byteStream()).items ?: emptyList())
        }
        return@withContext resultList
    }


}