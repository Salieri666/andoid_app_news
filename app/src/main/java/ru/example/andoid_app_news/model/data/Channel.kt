package ru.example.andoid_app_news.model.data

import ru.example.andoid_app_news.model.ui.News

data class Channel(val language: String? = null,
                   val title: String? = null,
                   val description: String? = null,
                   val link: String? = null,
                   val items: List<News>? = emptyList()
)
