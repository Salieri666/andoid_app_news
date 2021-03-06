package ru.example.andoid_app_news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.example.andoid_app_news.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mainBinding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        /*mainBinding?.let {
            val navHostFragment =
                supportFragmentManager.findFragmentById(it.navFragment.id) as NavHostFragment
            val navController = navHostFragment.navController
            it.bottomNavigatinView.setupWithNavController(navController)
        }*/
    }
}