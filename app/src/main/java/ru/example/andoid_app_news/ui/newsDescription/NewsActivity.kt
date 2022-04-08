package ru.example.andoid_app_news.ui.newsDescription

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.example.andoid_app_news.MainApplication
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.databinding.ActivityNewsBinding
import ru.example.andoid_app_news.model.ui.News
import ru.example.andoid_app_news.model.viewmodel.CurrentNewsViewModel
import ru.example.andoid_app_news.model.viewmodel.CurrentNewsViewModelFactory
import ru.example.andoid_app_news.service.BookmarksService


class NewsActivity : AppCompatActivity() {

    private val currentNewsViewModel: CurrentNewsViewModel by viewModels {
        CurrentNewsViewModelFactory(BookmarksService((application as MainApplication).bookmarksRepo))
    }

    private var newsBinding: ActivityNewsBinding? = null
    private var newsItem: News? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(newsBinding?.root)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        newsBinding?.arrowBack?.setOnClickListener {
            onBackPressed()
        }
        newsBinding?.let {
            newsItem = intent.extras?.getParcelable(NEWS)
            newsBinding?.newsTitleCommon?.text = newsItem?.title
            newsBinding?.newsDescription?.text = newsItem?.description
        }

        newsItem?.let { _newsItem ->
            currentNewsViewModel.getNewsByUrl(_newsItem.url).observe(this) { dbNews ->
                if (dbNews.id != null) {
                    newsBinding?.bookmarksNews?.setImageResource(R.drawable.ic_baseline_bookmark_24)
                    newsBinding?.bookmarksNews?.setOnClickListener {
                        currentNewsViewModel.remove(dbNews.id)
                    }
                } else {
                    newsBinding?.bookmarksNews?.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                    newsBinding?.bookmarksNews?.setOnClickListener {
                        currentNewsViewModel.add(_newsItem)
                    }
                }
            }
        }

        return super.onCreateView(parent, name, context, attrs)
    }

    companion object {
        const val NEWS = "NEWS"
    }
}