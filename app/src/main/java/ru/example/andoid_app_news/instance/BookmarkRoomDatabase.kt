package ru.example.andoid_app_news.instance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.example.andoid_app_news.dao.BookmarksDao
import ru.example.andoid_app_news.model.entity.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1, exportSchema = false)
abstract class BookmarkRoomDatabase : RoomDatabase() {

    abstract fun bookmarksDao(): BookmarksDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarkRoomDatabase? = null

        fun getDatabase(context: Context): BookmarkRoomDatabase {
            return INSTANCE ?:
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkRoomDatabase::class.java,
                        "bookmark"
                    ).build()
                    INSTANCE = instance

                    instance
                }
        }
    }
}