package ru.example.andoid_app_news.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.example.andoid_app_news.dao.BookmarksDao
import ru.example.andoid_app_news.model.entity.BookmarkEntity

class BookmarksRepo(
    private val bookmarksDao: BookmarksDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val allBookmarks: Flow<List<BookmarkEntity>> = bookmarksDao.getBookmarks().flowOn(ioDispatcher)

    fun getByUrl(url: String): Flow<BookmarkEntity?> = bookmarksDao.getBookmark(url).flowOn(ioDispatcher)

    suspend fun insert(bookmark: BookmarkEntity) = withContext(ioDispatcher) {
        bookmarksDao.insert(bookmark)
    }

    suspend fun delete(id: Int) = withContext(ioDispatcher) {
        bookmarksDao.deleteById(id)
    }
}