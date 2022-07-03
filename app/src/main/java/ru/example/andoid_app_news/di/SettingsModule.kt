package ru.example.andoid_app_news.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.example.andoid_app_news.service.SettingsService
import ru.example.andoid_app_news.service.SettingsServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindSettingsService(
        settingsService: SettingsServiceImpl
    ) : SettingsService
}