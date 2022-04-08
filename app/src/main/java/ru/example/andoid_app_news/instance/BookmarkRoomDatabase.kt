package ru.example.andoid_app_news.instance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.example.andoid_app_news.dao.BookmarksDao
import ru.example.andoid_app_news.model.data.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 2, exportSchema = false)
public abstract class BookmarkRoomDatabase : RoomDatabase() {

    abstract fun bookmarksDao(): BookmarksDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: BookmarkRoomDatabase? = null

        fun getDatabase(context: Context): BookmarkRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkRoomDatabase::class.java,
                    "bookmark"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}