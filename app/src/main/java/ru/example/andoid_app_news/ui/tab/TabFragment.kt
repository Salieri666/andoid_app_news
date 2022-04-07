package ru.example.andoid_app_news.ui.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.databinding.FragmentTabBinding
import ru.example.andoid_app_news.ui.news.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.newsDescription.NewsActivity
import ru.example.andoid_app_news.viewmodel.NewsViewModel

private const val SOURCE = "SOURCE"

class TabFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()

    private var newsAdapter: RecyclerNewsAdapter? = null
    private var binding: FragmentTabBinding? = null
    private var source: String? = null

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
        fun newInstance(source: String) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putString(SOURCE, source)
                }
            }
    }
}