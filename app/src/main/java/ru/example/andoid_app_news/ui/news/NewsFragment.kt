package ru.example.andoid_app_news.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import ru.example.andoid_app_news.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private val sources: List<String> = arrayListOf("All news", "Lenta", "Russia 24", "FoxNews", "NewsSource1", "NewsSource2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewsBinding = FragmentNewsBinding.inflate(inflater, container, false)

        val tabLayout: TabLayout = binding.tabLayout
        val newsAdapter = RecyclerNewsAdapter()

        with(binding) {
            recyclerNews.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                adapter = newsAdapter
            }

        }

        sources.forEach { el ->
            val tab = tabLayout.newTab()
            tab.text = el
            tabLayout.addTab(tab)
        }

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

}