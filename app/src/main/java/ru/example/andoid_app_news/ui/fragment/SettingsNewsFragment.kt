package ru.example.andoid_app_news.ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.example.andoid_app_news.R

class SettingsNewsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}