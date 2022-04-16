package ru.example.andoid_app_news.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.example.andoid_app_news.model.data.BookmarkEntity
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
data class News(
    val id: Int? ,
    var url: String,
    var title: String?,
    var description: String?,
    var date: String?,
    var img: String?,
    var source: String?,
    var sourceDate: Long?
) : Parcelable {

    constructor() : this(null, "", null, null, null, null, null, null)

    companion object {
        fun toNews(entity: BookmarkEntity): News {

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val timeString = ZonedDateTime.ofInstant(Instant.ofEpochSecond(((entity.sourceDate?:0) / 1000)), TimeZone.getDefault().toZoneId()).format(formatter)

            return News(
                entity.id,
                entity.url,
                entity.title,
                entity.description,
                timeString,
                entity.img,
                entity.source,
                entity.sourceDate
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
                news.sourceDate,
                createdDate
            )
        }
    }
}