package ru.example.andoid_app_news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.example.andoid_app_news.databinding.ActivityMainBinding
import ru.example.andoid_app_news.ui.pages.MainFragment

private const val MAIN_BACKSTACK = "MAIN_BACKSTACK"

class MainActivity : AppCompatActivity() {

    private var mainBinding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        if (savedInstanceState != null) {
            return
        }

        mainBinding?.let {
            supportFragmentManager.beginTransaction()
                .add(it.mainFrameContainer.id, MainFragment.newInstance())
                .addToBackStack(MAIN_BACKSTACK)
                .commit()
        }

    }
}