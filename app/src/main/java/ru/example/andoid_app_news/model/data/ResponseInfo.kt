package ru.example.andoid_app_news.model.data

data class ResponseInfo (
    var id: Long? = null,
    var username: String? = null,
    var token: String? = null,
    var isSuccess: Boolean = false
)