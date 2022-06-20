package ru.example.andoid_app_news.ui.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.example.andoid_app_news.model.data.NewsSources

class TabPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,
                      private var sources: MutableList<NewsSources>)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return sources.size
    }

    override fun createFragment(position: Int): Fragment {
        return TabFragment.newInstance(sources[position])
    }

    private var sourcesId = sources.map { it.hashCode().toLong() }

    override fun getItemId(position: Int): Long {
        return sourcesId[position]
    }

    override fun containsItem(itemId: Long): Boolean {
        return sourcesId.contains(itemId)
    }
}