package com.famgy.everything.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.famgy.everything.view.fragment.DemoListFragment
import com.famgy.everything.view.fragment.SongListFragment

const val DEMO_LIST_PAGE_INDEX = 0
const val SONG_LIST_PAGE_INDEX = 1

class HomeViewPagerAdapter(fragment: Fragment) :  FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        DEMO_LIST_PAGE_INDEX to { DemoListFragment() },
        SONG_LIST_PAGE_INDEX to { SongListFragment() }
    )

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    override fun getItemCount() = tabFragmentsCreators.size
}