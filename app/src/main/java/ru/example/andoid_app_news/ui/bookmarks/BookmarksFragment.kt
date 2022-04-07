package ru.example.andoid_app_news.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.databinding.FragmentBookmarksBinding
import ru.example.andoid_app_news.ui.news.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.newsDescription.NewsActivity
import ru.example.andoid_app_news.viewmodel.NewsViewModel


class BookmarksFragment : Fragment() {

    private var newsAdapter: RecyclerNewsAdapter? = null
    private var binding: FragmentBookmarksBinding? = null

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        newsAdapter = RecyclerNewsAdapter()
        newsAdapter?.setOnItemClickListener(object : RecyclerNewsAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), NewsActivity::class.java)
                intent.putExtra(NewsActivity.NEWS, newsViewModel.newsList.value?.get(position))
                startActivity(intent)
            }
        })

        binding?.let{
            it.recyclerNews.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                adapter = newsAdapter
            }

        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.newsList.observe(viewLifecycleOwner) {
            it?.let {
                newsAdapter?.refreshNews(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookmarksFragment()
    }
}