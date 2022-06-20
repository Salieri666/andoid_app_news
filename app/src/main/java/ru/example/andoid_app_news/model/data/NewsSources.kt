package ru.example.andoid_app_news.model.data

import android.content.Context
import android.content.SharedPreferences
import ru.example.andoid_app_news.R
import java.io.Serializable

enum class NewsSources(val id: Int) : Serializable {
    ALL(R.string.all),
    LENTA(R.string.lenta_title),
    RBC(R.string.rbc_title),
    TECH_NEWS(R.string.tech_news),
    NPLUS1(R.string.nplus_news);

    companion object {
        fun getList(sharedPref: SharedPreferences, context: Context?): List<NewsSources> {
            val array = enumValues<NewsSources>()
            val result = arrayListOf<NewsSources>()
            for (el in array) {
                if (el != ALL && sharedPref.getBoolean(context?.getString(el.id), true))
                    result.add(el)
            }
            return result
        }
    }
}