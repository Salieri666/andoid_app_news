package ru.example.andoid_app_news.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.example.andoid_app_news.dao.BookmarksDao
import ru.example.andoid_app_news.model.data.BookmarkEntity

class BookmarksRepo(private val bookmarksDao: BookmarksDao) {

    val allBookmarks: Flow<List<BookmarkEntity>> = bookmarksDao.getBookmarks()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(bookmark: BookmarkEntity) {
        bookmarksDao.insert(bookmark)
    }

}