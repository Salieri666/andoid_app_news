package ru.example.andoid_app_news.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    var url: String?,
    var description: String?,
    var title: String?,
    var date: String?,
    var img: String?,
    var source: String?
) : Parcelable