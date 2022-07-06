package ru.example.andoid_app_news.useCase

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.rss.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    fun getNews(type: NewsSources): Flow<List<News>> = flow {

        val response = newsRepo.getNewsBySourceType(type)
        val res = parseNews(response, type)

        emit(res)
    }.flowOn(defaultDispatcher)

    fun getAllNewsByList(list: List<NewsSources>): Flow<List<News>> = flow {

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
        emit(news)
    }.flowOn(defaultDispatcher)


    private fun getParserBySourceType(type: NewsSources) : AbstractRssParser {
        return when (type) {
            NewsSources.LENTA -> LentaRssParser()
            NewsSources.RBC -> RbcRssParser()
            NewsSources.TECH_NEWS -> TechRssParser()
            NewsSources.NPLUS1 -> NplusOneRssParser()
            NewsSources.ARZAMAS -> ArzamasRssParser()
            else -> throw IllegalArgumentException("The parser type -> $type not founded!")
        }
    }


    private fun parseNews(responseBody: ResponseBody, type: NewsSources): List<News> {
        val resultList = arrayListOf<News>()
        val parser = getParserBySourceType(type)
        resultList.addAll(parser.parse(responseBody.byteStream()).items ?: emptyList())

        return resultList
    }
}