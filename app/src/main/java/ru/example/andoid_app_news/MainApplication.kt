package ru.example.andoid_app_news

import android.app.Application
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.example.andoid_app_news.instance.BookmarkRoomDatabase
import ru.example.andoid_app_news.repository.BookmarksRepo
import java.io.File


class MainApplication : Application() {
    private val database by lazy { BookmarkRoomDatabase.getDatabase(this) }

    val bookmarksRepo by lazy { BookmarksRepo(database.bookmarksDao()) }

    val httpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val clientBuilder = OkHttpClient().newBuilder()


        return@lazy clientBuilder
            .addInterceptor(logging)
            .cache(
                Cache(
                    directory = File(applicationContext.cacheDir, "http_cache"),
                    maxSize = 50L * 1024L * 1024L // 50 MiB
                )
            )
            .build()
    }
}