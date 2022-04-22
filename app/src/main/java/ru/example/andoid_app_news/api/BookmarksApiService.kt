package ru.example.andoid_app_news.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import ru.example.andoid_app_news.model.data.BookmarkDto

interface BookmarksApiService {

    @GET("/api/bookmarks/getAll/{userId}")
    suspend fun getAllByUserId(@Path("userId") userId: Long) : Response<List<BookmarkDto>>

    @GET("/api/bookmarks/getById/{id}")
    suspend fun getById(@Path("id") id: Long) : Response<BookmarkDto>

    @POST("/api/bookmarks/save")
    suspend fun save(@Body bookmark: BookmarkDto) : Response<BookmarkDto>

    @POST("/api/bookmarks/saveAll")
    suspend fun saveAll(@Body bookmarks: List<BookmarkDto>) : Response<List<BookmarkDto>>

    @DELETE("/api/bookmarks/delete/{id}")
    suspend fun delete(@Path("id") id: Long)

    companion object {
        fun instance(retrofit: Retrofit): BookmarksApiService  {
            return retrofit.create(BookmarksApiService::class.java)
        }
    }
}