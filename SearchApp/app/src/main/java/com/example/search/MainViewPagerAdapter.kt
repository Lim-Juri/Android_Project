package com.example.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.search.data.MainTabs

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    val fragments = ArrayList<MainTabs>()

    init {
        fragments.add(
            MainTabs(SearchFragment(), "검색 결과", R.drawable.search)
        )
        fragments.add(
            MainTabs(BookmarkFragment(), "내 보관함", R.drawable.box)
        )
    }

    fun getTitle(position: Int): String {
        return fragments[position].titleRes
    }

    fun getIcon(position: Int): Int {
        return fragments[position].iconRes
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }
}

