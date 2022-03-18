package ru.example.andoid_app_news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.example.andoid_app_news.databinding.ActivityMainBinding

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
            val navHostFragment =
                supportFragmentManager.findFragmentById(it.navFragment.id) as NavHostFragment
            val navController = navHostFragment.navController
            it.bottomNavigatinView.setupWithNavController(navController)
        }
    }
}