package ru.example.andoid_app_news.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.MainApplication
import ru.example.andoid_app_news.databinding.FragmentBookmarksBinding
import ru.example.andoid_app_news.ui.activity.NewsActivity
import ru.example.andoid_app_news.ui.adapter.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.viewmodel.BookmarksViewModel
import ru.example.andoid_app_news.ui.viewmodel.BookmarksViewModelFactory
import ru.example.andoid_app_news.useCase.BookmarksUseCase


class BookmarksFragment : Fragment() {

    private var newsAdapter: RecyclerNewsAdapter? = null
    private var binding: FragmentBookmarksBinding? = null

    private val bookmarksViewModel: BookmarksViewModel by viewModels {
        BookmarksViewModelFactory(BookmarksUseCase((activity?.application as MainApplication).bookmarksRepo))
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            bookmarksViewModel.allBookmarks.collect {
                newsAdapter?.submitList(it)
            }
        }

        bookmarksViewModel.loadBookmarks()

        /*bookmarksViewModel.allBookmarks
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> newsAdapter?.submitList(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)*/
    }


    override fun onDestroyView() {
        super.onDestroyView()
        newsAdapter = null
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookmarksFragment()
    }

    private fun onNewsClick(position: Int) {
        val intent = Intent(requireContext(), NewsActivity::class.java)
        intent.putExtra(NewsActivity.NEWS,
            bookmarksViewModel.allBookmarks.value[position]
        )
        startActivity(intent)
    }

    private fun setupRecycler() {
        newsAdapter = RecyclerNewsAdapter()
        newsAdapter?.itemClickListener = ::onNewsClick

        binding?.let{
            it.recyclerNews.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                adapter = newsAdapter
            }

        }
    }
}