package ru.example.andoid_app_news.model.data

data class BookmarkDto(
    var id: Long?,
    var url: String?,
    var title: String?,
    var description: String?,
    var img: String?,
    var source: String?,
    var sourceDate: Long?,
    var createdDate: Long?,
    var userId: Long?
)
