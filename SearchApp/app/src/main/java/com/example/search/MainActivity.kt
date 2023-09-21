package com.example.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.search.data.KakaoImage
import com.example.search.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // 좋아요 저장 리스트
    var likedItems: ArrayList<KakaoImage> = ArrayList()

    private val viewPagerAdapter by lazy {
        MainViewPagerAdapter(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    fun initView() = with(binding) {

        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
            tab.setIcon(viewPagerAdapter.getIcon(position))
        }.attach()
    }

    fun addLikedItem(item: KakaoImage) {
        if (!likedItems.contains(item)) {
            likedItems.add(item)
        }
    }

    fun removeLikedItem(item: KakaoImage) {
        likedItems.remove(item)
    }
}

