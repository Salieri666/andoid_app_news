package ru.example.andoid_app_news.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewsBinding = FragmentNewsBinding.inflate(inflater, container, false)
        val noteAdapter = RecyclerNewsAdapter()

        with(binding) {
            recyclerNews.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                adapter = noteAdapter
            }

        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}