package ru.example.andoid_app_news.dao.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.example.andoid_app_news.dao.BookmarksDao
import ru.example.andoid_app_news.model.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1, exportSchema = false)
abstract class BookmarkRoomDatabase : RoomDatabase() {

    abstract fun bookmarksDao(): BookmarksDao

}