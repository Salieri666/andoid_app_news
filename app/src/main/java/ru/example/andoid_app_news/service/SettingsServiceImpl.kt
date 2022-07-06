package ru.example.andoid_app_news.service

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.example.andoid_app_news.model.data.NewsSources
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsServiceImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : SettingsService {

    private val sharedPref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun checkKey(key: String): Boolean {
        return sharedPref.getBoolean(key, false);
    }

    override fun getAllowedNewsSources(withAll: Boolean): List<NewsSources> {
        val array = enumValues<NewsSources>()
        val result = mutableListOf<NewsSources>()

        if (withAll)
            result.add(NewsSources.ALL)

        for (el in array) {
            if (el != NewsSources.ALL && sharedPref.getBoolean(appContext.getString(el.id), true))
                result.add(el)
        }
        return result
    }
}