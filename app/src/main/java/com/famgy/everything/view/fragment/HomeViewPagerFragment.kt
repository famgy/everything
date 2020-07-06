package com.famgy.everything.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.famgy.everything.R
import com.famgy.everything.common.Exit_TIME
import com.famgy.everything.common.uitils.InjectorUtil
import com.famgy.everything.databinding.FragmentHomeViewPagerBinding
import com.famgy.everything.view.adapter.DEMO_LIST_PAGE_INDEX
import com.famgy.everything.view.adapter.HomeViewPagerAdapter
import com.famgy.everything.view.adapter.SONG_LIST_PAGE_INDEX
import com.famgy.everything.viewmodel.LoginViewModel
import com.google.android.material.tabs.TabLayoutMediator


class HomeViewPagerFragment : Fragment() {

    private var exitTime = 0L
    private val viewModle: LoginViewModel by viewModels {
        InjectorUtil.provideLoginViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = HomeViewPagerAdapter(this)

        //Traversing the viewPager
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        //xml layout add : android:fitsSystemWindows="true"
        var window = activity?.getWindow()
        window?.statusBarColor = Color.TRANSPARENT
        window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if ((System.currentTimeMillis() - exitTime) > Exit_TIME) {
                Toast.makeText(context, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis()
            } else {
                requireActivity().finish()
            }
        }
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            DEMO_LIST_PAGE_INDEX -> R.drawable.song_list_tab_selector
            SONG_LIST_PAGE_INDEX -> R.drawable.song_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            DEMO_LIST_PAGE_INDEX -> getString(R.string.demo_list_title)
            SONG_LIST_PAGE_INDEX -> getString(R.string.song_list_title)
            else -> null
        }
    }
}