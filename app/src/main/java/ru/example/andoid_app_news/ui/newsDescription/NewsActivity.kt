package ru.example.andoid_app_news.ui.newsDescription

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.example.andoid_app_news.databinding.ActivityNewsBinding
import ru.example.andoid_app_news.model.ui.News


class NewsActivity : AppCompatActivity() {

    private var newsBinding: ActivityNewsBinding? = null
    private var newsItem: News? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding = ActivityNewsBinding.inflate(layoutInflater)
        newsBinding?.arrowBack?.setOnClickListener {
            onBackPressed()
        }

        setContentView(newsBinding?.root)

        newsBinding?.let {
            newsItem = intent.extras?.getParcelable(NEWS)
            newsBinding?.newsTitleCommon?.text = newsItem?.title
            newsBinding?.newsDescription?.text = newsItem?.description
        }
    }

    companion object {
        const val NEWS = "NEWS"
    }
}