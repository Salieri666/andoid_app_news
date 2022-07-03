package ru.example.andoid_app_news.ui.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import dagger.hilt.android.AndroidEntryPoint
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.databinding.FragmentSelectedNewsBinding
import ru.example.andoid_app_news.model.data.News
import ru.example.andoid_app_news.ui.viewmodel.CurrentNewsViewModel

private const val SELECTED_NEWS = "SELECTED_NEWS"

@AndroidEntryPoint
class SelectedNewsFragment : Fragment() {
    private val currentNewsViewModel: CurrentNewsViewModel by viewModels()

    private var newsItem: News? = null
    private var newsBinding: FragmentSelectedNewsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsItem = it.getParcelable(SELECTED_NEWS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsBinding = FragmentSelectedNewsBinding.inflate(inflater, container, false)
        setupBackArrow()
        fillNewsData()
        checkIfBookmarkExists()
        return newsBinding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        newsBinding = null
        newsItem = null
    }

    private fun setupBackArrow() {
        newsBinding?.arrowBack?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun fillNewsData() {
        newsBinding?.let {
            it.newsTitleCommon.text = newsItem?.title
            it.newsDescription.text = Html.fromHtml(newsItem?.description ?: "", Html.FROM_HTML_MODE_COMPACT)
            it.newsDateActivity.text = newsItem?.date
            it.newsSourceActivity.text = newsItem?.source

            if (newsItem?.img == null)
                it.imageNews.visibility = View.GONE
            else {
                Glide.with(requireContext())
                    .asBitmap()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .centerCrop()
                    .load(newsItem?.img)
                    .into(it.imageNews)
            }
        }
    }

    private fun checkIfBookmarkExists() {
        newsItem?.let { _newsItem ->

            lifecycleScope.launchWhenStarted {
                currentNewsViewModel.selectedNews.collect { dbNews ->
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

            currentNewsViewModel.getNewsByUrl(_newsItem.url)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(selectedNews: String) =
            SelectedNewsFragment().apply {
                arguments = Bundle().apply {
                    putString(SELECTED_NEWS, selectedNews)
                }
            }
    }
}