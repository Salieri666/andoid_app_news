package ru.example.andoid_app_news.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.example.andoid_app_news.dao.BookmarksDao
import ru.example.andoid_app_news.dao.database.BookmarkRoomDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext appContext: Context
    ) : BookmarkRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            BookmarkRoomDatabase::class.java,
            "bookmark"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarksDao(
        appDatabase: BookmarkRoomDatabase
    ) : BookmarksDao {
        return appDatabase.bookmarksDao()
    }
}