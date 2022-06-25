package ru.example.andoid_app_news.useCase

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.model.data.ResultData
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.rss.*

private const val ERROR_TYPE = "ERROR_NEWS_USE_CASE"

class NewsUseCase(
    private val newsRepo: NewsRepo,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    fun getNews(type: NewsSources): Flow<ResultData<List<News>>> = flow {

        val response = newsRepo.getNewsBySourceType(type)
        val res = parseNews(response, type)

        emit(ResultData(ResultData.Status.SUCCESSES, res))

    }.onStart {
        emit(ResultData(ResultData.Status.LOADING))
    }.catch {
        Log.e(ERROR_TYPE, it.localizedMessage, it)
        ResultData(ResultData.Status.FAILED, emptyList<News>())
    }.flowOn(defaultDispatcher)

    fun getAllNewsByList(list: List<NewsSources>): Flow<ResultData<List<News>>> = flow {

        val news = arrayListOf<News>()
        coroutineScope {

            list.mapIndexed  { _, source ->
                async {
                    val response = newsRepo.getNewsBySourceType(source)
                    news.addAll(parseNews(response, source))
                }
            }.awaitAll()

            news.sortByDescending { el -> el.sourceDate }
        }
        emit(ResultData(ResultData.Status.SUCCESSES, news as List<News>))

    }.onStart {
        emit(ResultData(ResultData.Status.LOADING))
    }.catch {
        Log.e(ERROR_TYPE, it.localizedMessage, it)
        ResultData(ResultData.Status.FAILED, emptyList<News>())
    }.flowOn(defaultDispatcher)


    private fun getParserBySourceType(type: NewsSources) : AbstractRssParser {
        return when (type) {
            NewsSources.LENTA -> LentaRssParser()
            NewsSources.RBC -> RbcRssParser()
            NewsSources.TECH_NEWS -> TechRssParser()
            NewsSources.NPLUS1 -> NplusOneRssParser()
            else -> throw IllegalArgumentException("The parser type -> $type not founded!")
        }
    }


    private fun parseNews(responseBody: ResponseBody, type: NewsSources): List<News> {
        val resultList = arrayListOf<News>()
        try {
            val parser = getParserBySourceType(type)
            Log.v("Context1", "Parsing...  " + Thread.currentThread().name)
            resultList.addAll(parser.parse(responseBody.byteStream()).items ?: emptyList())
        } catch (e: Exception) {
            Log.e(ERROR_TYPE, e.localizedMessage, e)
        }
        return resultList
    }
}