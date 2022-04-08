package ru.example.andoid_app_news.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.repository.BookmarksRepo

class BookmarksService(private val bookmarksRepo: BookmarksRepo) {

    suspend fun add(news: News) {
        bookmarksRepo.insert(News.toEntity(news))
    }

    fun getAll() : Flow<List<News>>  {
        return bookmarksRepo.allBookmarks.map { list ->
            list.map {
                News.toNews(it)
            }
        }
    }

    suspend fun remove(news: News) {}
}