package ru.example.andoid_app_news.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmark",
    indices = [
        Index(value = ["url"], unique = true)
    ]
)
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "img") val img: String?,
    @ColumnInfo(name = "source") val source: String?,
    @ColumnInfo(name = "source_date") val sourceDate: String?,
    @ColumnInfo(name = "created_date") val createdDate: Long?
)
