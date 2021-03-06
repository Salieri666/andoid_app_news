package ru.example.andoid_app_news.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.databinding.FragmentTabBinding
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.ui.adapter.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.viewmodel.NewsViewModel

private const val SOURCE = "SOURCE"

@AndroidEntryPoint
class TabFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()

    private var newsAdapter: RecyclerNewsAdapter? = null
    private var binding: FragmentTabBinding? = null
    private var source: NewsSources? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            source = it.getParcelable(SOURCE) as NewsSources?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabBinding.inflate(inflater, container, false)

        setupRecycler()
        setupRefresh()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadingIcon()
        setupLoadNews()
        setupRefreshingIcon()
        loadNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsViewModel.isLoading.removeObservers(viewLifecycleOwner)
        newsViewModel.isRefreshing.removeObservers(viewLifecycleOwner)
        binding = null
        newsAdapter = null
    }

    companion object {
        @JvmStatic
        fun newInstance(source: NewsSources) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SOURCE, source)
                }
            }
    }

    private fun onNewsClick(position: Int) {
        val selectedNews = newsViewModel.news.value[position]
        val bundle = bundleOf("SELECTED_NEWS" to selectedNews)

        Navigation.findNavController(requireActivity(), R.id.nav_fragment2).navigate(R.id.action_bottomNavigationFragment_to_selectedNewsFragment3, bundle)
    }


    private fun setupRecycler() {
        newsAdapter = RecyclerNewsAdapter()
        newsAdapter?.itemClickListener = ::onNewsClick

        binding?.let {
            it.recyclerNewsTab.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = newsAdapter
            }
        }
    }

    private fun setupRefresh() {
        binding?.swipeRefresh?.setOnRefreshListener {
            loadNews(true)
        }
    }

    private fun setupLoadingIcon() {
        newsViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding?.loadingNewsIcon?.visibility = View.VISIBLE
            } else {
                binding?.loadingNewsIcon?.visibility = View.GONE
            }
        }
    }

    private fun setupRefreshingIcon() {
        newsViewModel.isRefreshing.observe(viewLifecycleOwner) {
            binding?.swipeRefresh?.isRefreshing = it
        }
    }

    private fun setupLoadNews() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            newsViewModel.news.collect {
                newsAdapter?.submitList(it)
            }
        }
    }

    private fun loadNews(isRefresh: Boolean = false) {
        source?.let { newsSource ->
            newsViewModel.loadNews(newsSource, isRefresh)
        }
    }
}