package ru.example.andoid_app_news.service

import ru.example.andoid_app_news.model.data.NewsSources

interface SettingsService {

    fun checkKey(key: String) : Boolean

    fun getAllowedNewsSources() : List<NewsSources>
}