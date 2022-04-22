package ru.example.andoid_app_news.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import ru.example.andoid_app_news.model.data.AuthDto
import ru.example.andoid_app_news.model.data.ResponseInfo


interface ProfileApiService {

    @POST("/api/login")
    suspend fun login(@Body authDto: AuthDto) : Response<ResponseInfo>

    @POST("/api/registration")
    suspend fun registration(@Body authDto: AuthDto) : Response<ResponseInfo>

    companion object {
        fun instance(retrofit: Retrofit): ProfileApiService  {
            return retrofit.create(ProfileApiService::class.java)
        }
    }
}




