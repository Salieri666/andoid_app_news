package ru.example.andoid_app_news.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.example.andoid_app_news.databinding.FragmentNewsBinding

import ru.example.andoid_app_news.model.data.NewsSources
import ru.example.andoid_app_news.ui.adapter.TabPagerAdapter

class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null
    private var tabLayoutMediator: TabLayoutMediator? = null
    private var adapter: TabPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        val sources = prepareSourceList()
        setupTabs(sources)

        return binding?.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        adapter = null
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

    private fun prepareSourceList() : List<NewsSources> {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val sources = NewsSources.getList(sharedPref, requireContext())
        sources.add(0, NewsSources.ALL)
        return sources
    }

    private fun setupTabs(sources: List<NewsSources>) {
        binding?.let {
            val tabLayout: TabLayout = it.tabLayout
            val pager: ViewPager2 = it.pager

            adapter = TabPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, sources)
            pager.adapter = adapter

            tabLayoutMediator = TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = getString(sources[position].id)
            }
            tabLayoutMediator?.attach()
        }
    }
}