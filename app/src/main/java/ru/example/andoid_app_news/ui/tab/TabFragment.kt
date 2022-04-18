package ru.example.andoid_app_news.ui.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.MainApplication
import ru.example.andoid_app_news.databinding.FragmentTabBinding
import ru.example.andoid_app_news.model.viewmodel.NewsViewModel
import ru.example.andoid_app_news.model.viewmodel.NewsViewModelFactory
import ru.example.andoid_app_news.repository.NewsRepo
import ru.example.andoid_app_news.ui.news.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.newsDescription.NewsActivity

private const val SOURCE = "SOURCE"

class TabFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(NewsRepo((activity?.application as MainApplication).httpClient), source?: "")
    }

    private var newsAdapter: RecyclerNewsAdapter? = null
    private var binding: FragmentTabBinding? = null
    private var source: String? = null
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            source = it.getString(SOURCE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabBinding.inflate(inflater, container, false)

        setupRecycler()

        loadingDialog = LoadingDialog(inflater, context, container)
        loadingDialog?.show()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        source?.let {
            newsViewModel.newsList.observe(viewLifecycleOwner) {
                it?.let {
                    newsAdapter?.submitList(it)
                }
            }
        }

        newsViewModel.isLoading.observe(viewLifecycleOwner) {
            if (!it) {
                loadingDialog?.close()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsViewModel.newsList.removeObservers(viewLifecycleOwner)
        newsViewModel.isLoading.removeObservers(viewLifecycleOwner)
    }

    companion object {
        @JvmStatic
        fun newInstance(source: String) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putString(SOURCE, source)
                }
            }
    }

    private fun setupRecycler() {
        newsAdapter = RecyclerNewsAdapter()
        newsAdapter?.setOnItemClickListener(object : RecyclerNewsAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), NewsActivity::class.java)
                intent.putExtra(NewsActivity.NEWS, newsViewModel.newsList.value?.get(position))
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

}