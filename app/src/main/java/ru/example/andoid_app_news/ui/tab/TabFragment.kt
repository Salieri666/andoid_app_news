package ru.example.andoid_app_news.ui.tab

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.example.andoid_app_news.databinding.FragmentTabBinding
import ru.example.andoid_app_news.ui.news.RecyclerNewsAdapter
import ru.example.andoid_app_news.ui.newsDescription.NewsActivity

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
        newsAdapter.setOnItemClickListener(object : RecyclerNewsAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), NewsActivity::class.java)
                intent.putExtra("position", position)
                startActivity(intent)
            }
        })

        with(binding) {
            recyclerNewsTab.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                adapter = newsAdapter
            }
        }


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