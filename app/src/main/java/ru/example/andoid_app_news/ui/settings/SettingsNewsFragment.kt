package ru.example.andoid_app_news.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import ru.example.andoid_app_news.R

class SettingsNewsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = activity?.getSharedPreferences("APP_PREFERENCES",
            Context.MODE_PRIVATE)
        val pref = findPreference<PreferenceScreen>("screen_profile_preferences")
        sharedPreferences?.let { sh ->
            pref?.let {
                if (sh.getBoolean("is_auth", false)) {
                    it.summary = sh.getString("login", "")
                } else {
                    it.summary = "Not logged in"
                }
            }
        }
    }
}