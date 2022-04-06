package ru.example.andoid_app_news.ui.newsDescription

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.example.andoid_app_news.databinding.ActivityNewsBinding


class NewsActivity : AppCompatActivity() {

    private var newsBinding: ActivityNewsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding = ActivityNewsBinding.inflate(layoutInflater)
        newsBinding?.arrowBack?.setOnClickListener {
            onBackPressed()
        }

        setContentView(newsBinding?.root)

        savedInstanceState?.let {
            newsBinding?.newsTitleCommon?.text = it.getString(NEWS_TITLE)
            newsBinding?.newsDescription?.text = it.getString(NEWS_DESCRIPTION)
        }
    }

    companion object {
        const val NEWS_TITLE = "NEWS_TITLE"
        const val NEWS_DESCRIPTION = "NEWS_DESCRIPTION"
    }
}