package ru.example.andoid_app_news

import android.app.Application
import ru.example.andoid_app_news.instance.BookmarkRoomDatabase
import ru.example.andoid_app_news.repository.BookmarksRepo

class MainApplication : Application() {
    private val database by lazy { BookmarkRoomDatabase.getDatabase(this) }
    val bookmarksRepo by lazy { BookmarksRepo(database.bookmarksDao()) }
}