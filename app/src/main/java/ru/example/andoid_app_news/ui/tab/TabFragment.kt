package ru.example.andoid_app_news.ui.tab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.databinding.FragmentTabBinding
import ru.example.andoid_app_news.ui.news.RecyclerNewsAdapter

private const val SOURCE = "SOURCE"

class TabFragment : Fragment() {
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
        val binding : FragmentTabBinding = FragmentTabBinding.inflate(inflater, container, false)

        val newsAdapter = RecyclerNewsAdapter()

        with(binding) {
            recyclerNewsTab.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                adapter = newsAdapter
            }

        }

        Log.d("TABS", "Fragment created")

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putString(SOURCE, param1)
                }
            }
    }
}