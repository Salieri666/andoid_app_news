package ru.example.andoid_app_news.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.example.andoid_app_news.R
import ru.example.andoid_app_news.databinding.FragmentBottomNaviagtionBinding


class BottomNavigationFragment : Fragment() {

    private var binding : FragmentBottomNaviagtionBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomNaviagtionBinding.inflate(inflater, container, false)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding?.bottomNavigationView?.setupWithNavController(navController)

        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = BottomNavigationFragment()
    }
}