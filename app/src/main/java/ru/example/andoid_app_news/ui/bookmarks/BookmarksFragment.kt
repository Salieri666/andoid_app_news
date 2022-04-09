package ru.example.andoid_app_news.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.MainApplication
import ru.example.andoid_app_news.databinding.FragmentBookmarksBinding
import ru.example.andoid_app_news.model.viewmodel.BookmarksViewModel
import ru.example.andoid_app_news.model.viewmodel.BookmarksViewModelFactory
import ru.example.andoid_app_news.service.BookmarksService
import ru.example.andoid_app_news.ui.news.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.newsDescription.NewsActivity


class BookmarksFragment : Fragment() {

    private var newsAdapter: RecyclerNewsAdapter? = null
    private var binding: FragmentBookmarksBinding? = null

    private val newsViewModel: BookmarksViewModel by viewModels {
        BookmarksViewModelFactory(BookmarksService((activity?.application as MainApplication).bookmarksRepo))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        setupRecycler()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.allBookmarks.observe(viewLifecycleOwner) {
            it?.let {
                newsAdapter?.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsViewModel.allBookmarks.removeObservers(viewLifecycleOwner)
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookmarksFragment()
    }

    private fun setupRecycler() {
        newsAdapter = RecyclerNewsAdapter()
        newsAdapter?.setOnItemClickListener(object : RecyclerNewsAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), NewsActivity::class.java)
                intent.putExtra(NewsActivity.NEWS, newsViewModel.allBookmarks.value?.get(position))
                startActivity(intent)
            }
        })

        binding?.let{
            it.recyclerNews.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                adapter = newsAdapter
            }

        }
    }
}