package ru.example.andoid_app_news.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.databinding.FragmentBookmarksBinding
import ru.example.andoid_app_news.ui.news.RecyclerNewsAdapter


class BookmarksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentBookmarksBinding = FragmentBookmarksBinding.inflate(inflater, container, false)
        val newsAdapter = RecyclerNewsAdapter()

        with(binding) {
            recyclerNews.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                adapter = newsAdapter
            }

        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = BookmarksFragment()
    }
}