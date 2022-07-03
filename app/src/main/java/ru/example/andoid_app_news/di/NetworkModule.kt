package ru.example.andoid_app_news.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideClient(
        @ApplicationContext applicationContext: Context
    ) : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val clientBuilder = OkHttpClient().newBuilder()


        return clientBuilder
            .addInterceptor(logging)
            .cache(
                Cache(
                    directory = File(applicationContext.cacheDir, "http_cache"),
                    maxSize = 50L * 1024L * 1024L // 50 MiB
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        httpClient : OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}