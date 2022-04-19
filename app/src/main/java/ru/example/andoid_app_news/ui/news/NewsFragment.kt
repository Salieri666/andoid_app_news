package ru.example.andoid_app_news.ui.news

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
import ru.example.andoid_app_news.ui.tab.TabPagerAdapter

class NewsFragment : Fragment() {

    private val sources: List<String> = arrayListOf("All", "Lenta")
    private var binding: FragmentNewsBinding? = null
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding?.let {
            val tabLayout: TabLayout = it.tabLayout
            val pager: ViewPager2 = it.pager

            val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            val checkedSources = mutableListOf<String>()
            sources.forEach { el ->
                if (el == "All") {
                    checkedSources.add(el)
                } else {
                    if (sharedPref != null && sharedPref.getBoolean(el, false)) {
                        checkedSources.add(el)
                    }
                }
            }

            pager.adapter = TabPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle, checkedSources)

            tabLayoutMediator = TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = checkedSources[position]
            }
            tabLayoutMediator?.attach()
        }

        return binding?.root
    }


    override fun onDestroyView() {
        tabLayoutMediator?.detach()
        binding?.pager?.adapter = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }

}