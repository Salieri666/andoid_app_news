package ru.example.andoid_app_news.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import ru.example.andoid_app_news.dao.BookmarksDao
import ru.example.andoid_app_news.model.entity.BookmarkEntity

class BookmarksRepo(private val bookmarksDao: BookmarksDao) {

    val allBookmarks: Flow<List<BookmarkEntity>> = bookmarksDao.getBookmarks()

    fun getByUrl(url: String): Flow<BookmarkEntity?> {
        return bookmarksDao.getBookmark(url)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(bookmark: BookmarkEntity) {
        bookmarksDao.insert(bookmark)
    }

    @WorkerThread
    suspend fun delete(id: Int) {
        bookmarksDao.deleteById(id)
    }
}