package ru.example.andoid_app_news.ui

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.example.andoid_app_news.dao.BookmarksDao
import ru.example.andoid_app_news.instance.BookmarkRoomDatabase
import ru.example.andoid_app_news.model.entity.BookmarkEntity
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TestBookmarksDatabase {

    private lateinit var bookmarksDao: BookmarksDao
    private lateinit var db: BookmarkRoomDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BookmarkRoomDatabase::class.java).build()
        bookmarksDao = db.bookmarksDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeReadRemoveBookmarks() = runBlocking {
        val url = "test_url"
        val bookmark = BookmarkEntity(null, url, "", "", "", "", 1, 2)

        bookmarksDao.insert(bookmark)
        val myBookmark = bookmarksDao.getBookmark("test_url").first()

        assertNotNull(myBookmark)
        assertEquals(myBookmark?.url, url)

        bookmarksDao.deleteById(myBookmark?.id ?: 0)
        val removed = bookmarksDao.getBookmark("test_url").first()
        assertNull(removed)
    }
}