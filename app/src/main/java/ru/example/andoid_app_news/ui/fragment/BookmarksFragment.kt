package ru.example.andoid_app_news.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.databinding.FragmentBookmarksBinding
import ru.example.andoid_app_news.ui.adapter.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.viewmodel.BookmarksViewModel

@AndroidEntryPoint
class BookmarksFragment : Fragment() {

    private var newsAdapter: RecyclerNewsAdapter? = null
    private var binding: FragmentBookmarksBinding? = null

    private val bookmarksViewModel: BookmarksViewModel by viewModels()

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
        val selectedNews = bookmarksViewModel.allBookmarks.value[position]
        val bundle = bundleOf("SELECTED_NEWS" to selectedNews)

        findNavController().navigate(R.id.action_bookmarksFragment_to_selectedNewsFragment, bundle)
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