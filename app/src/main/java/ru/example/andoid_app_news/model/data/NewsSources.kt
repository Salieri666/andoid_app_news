package ru.example.andoid_app_news.model.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.example.andoid_app_news.R

@Parcelize
enum class NewsSources(val id: Int) : Parcelable {
    ALL(R.string.all),
    LENTA(R.string.lenta_title),
    RBC(R.string.rbc_title),
    TECH_NEWS(R.string.tech_news),
    NPLUS1(R.string.nplus_news);

    companion object {
        fun getList(sharedPref: SharedPreferences, context: Context?): MutableList<NewsSources> {
            val array = enumValues<NewsSources>()
            val result = mutableListOf<NewsSources>()
            for (el in array) {
                if (el != ALL && sharedPref.getBoolean(context?.getString(el.id), true))
                    result.add(el)
            }
            return result
        }
    }
}