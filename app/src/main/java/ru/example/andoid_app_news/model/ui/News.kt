package ru.example.andoid_app_news.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.example.andoid_app_news.model.data.BookmarkEntity

@Parcelize
data class News(
    val id: Int? ,
    var url: String,
    var title: String?,
    var description: String?,
    var date: String?,
    var img: String?,
    var source: String?
) : Parcelable {

    constructor() : this(null, "", null, null, null, null, null)

    companion object {
        fun toNews(entity: BookmarkEntity): News {
            return News(
                entity.id,
                entity.url,
                entity.title,
                entity.description,
                entity.sourceDate,
                entity.img,
                entity.source
            )
        }

        fun toEntity(news: News, createdDate: Long): BookmarkEntity {
            return BookmarkEntity(
                null,
                news.url,
                news.title,
                news.description,
                news.img,
                news.source,
                news.date,
                createdDate
            )
        }
    }
}