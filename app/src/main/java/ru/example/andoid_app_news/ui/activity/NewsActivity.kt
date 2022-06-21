package ru.example.andoid_app_news.ui.activity

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.util.AttributeSet
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import ru.example.andoid_app_news.MainApplication
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.databinding.ActivityNewsBinding
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.ui.viewmodel.CurrentNewsViewModel
import ru.example.andoid_app_news.ui.viewmodel.CurrentNewsViewModelFactory
import ru.example.andoid_app_news.useCase.BookmarksUseCase


class NewsActivity : AppCompatActivity() {

    private val currentNewsViewModel: CurrentNewsViewModel by viewModels {
        CurrentNewsViewModelFactory(BookmarksUseCase((application as MainApplication).bookmarksRepo))
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
            it.newsTitleCommon.text = newsItem?.title
            it.newsDescription.text = Html.fromHtml(newsItem?.description ?: "", Html.FROM_HTML_MODE_LEGACY)
            it.newsDateActivity.text = newsItem?.date
            it.newsSourceActivity.text = newsItem?.source

            if (newsItem?.img == null)
                it.imageNews.visibility = View.GONE
            else {
                Picasso.get().load(newsItem?.img).into(it.imageNews)
            }
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