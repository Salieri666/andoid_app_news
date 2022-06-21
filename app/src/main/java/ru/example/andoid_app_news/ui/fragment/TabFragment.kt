package ru.example.andoid_app_news.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import ru.example.andoid_app_news.MainApplication
import ru.example.andoid_app_news.api.NewsApiService
import ru.example.andoid_app_news.databinding.FragmentTabBinding
import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.ui.activity.NewsActivity
import ru.example.andoid_app_news.ui.adapter.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.dialogs.LoadingDialog
import ru.example.andoid_app_news.ui.viewmodel.NewsViewModel
import ru.example.andoid_app_news.ui.viewmodel.NewsViewModelFactory
import ru.example.andoid_app_news.useCase.NewsUseCase

private const val SOURCE = "SOURCE"

class TabFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(
            NewsUseCase(
                NewsRepo(NewsApiService.instance((activity?.application as MainApplication).retrofit))
            ))
    }

    private var newsAdapter: RecyclerNewsAdapter? = null
    private var binding: FragmentTabBinding? = null
    private var source: NewsSources? = null
    private var loadingDialog: LoadingDialog? = null
    private var newsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            source = it.getSerializable(SOURCE) as NewsSources?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabBinding.inflate(inflater, container, false)

        setupRecycler()
        loadingDialog = LoadingDialog(inflater, context, container)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLoadingDialog()
        setupLoadNews()
    }

    override fun onPause() {
        super.onPause()
        newsJob?.cancel()
        loadingDialog?.close()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsJob?.cancel()
        newsViewModel.isLoading.removeObservers(viewLifecycleOwner)
    }

    companion object {
        @JvmStatic
        fun newInstance(source: NewsSources) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SOURCE, source)
                }
            }
    }

    private fun setupRecycler() {
        newsAdapter = RecyclerNewsAdapter()
        newsAdapter?.setOnItemClickListener(object : RecyclerNewsAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), NewsActivity::class.java)
                intent.putExtra(NewsActivity.NEWS, newsViewModel.news.value[position])
                startActivity(intent)
            }
        })

        binding?.let {
            it.recyclerNewsTab.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = newsAdapter
            }
        }
    }

    private fun setupLoadingDialog() {
        newsViewModel.isLoading.observe(viewLifecycleOwner) {
            if (!it) {
                loadingDialog?.close()
            } else {
                loadingDialog?.show()
            }
        }
    }

    private fun setupLoadNews() {
        source?.let {  newsSource ->
            newsJob = lifecycleScope.launchWhenResumed {
                newsViewModel.news.collect {
                    newsAdapter?.submitList(it)
                }
            }

            if (newsSource != NewsSources.ALL) {
                newsViewModel.loadNews(newsSource)
            } else {
                val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
                val sources = NewsSources.getList(sharedPref, context)
                newsViewModel.loadNews(sources)
            }
        }
    }
}