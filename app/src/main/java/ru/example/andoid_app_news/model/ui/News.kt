package ru.example.andoid_app_news.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.example.andoid_app_news.model.data.BookmarkEntity

@Parcelize
data class News(
    val id: Int?,
    var url: String,
    var description: String?,
    var title: String?,
    var date: String?,
    var img: String?,
    var source: String?
) : Parcelable {

    companion object {
        fun toNews(entity: BookmarkEntity): News {
            return News(
                entity.id,
                entity.url,
                entity.description,
                entity.title,
                entity.sourceDate,
                entity.img,
                entity.source
            )
        }

        fun toEntity(news: News): BookmarkEntity {
            return BookmarkEntity(
                null,
                news.url,
                news.description,
                news.title,
                news.img,
                news.source,
                news.date,
                null
            )
        }
    }
}