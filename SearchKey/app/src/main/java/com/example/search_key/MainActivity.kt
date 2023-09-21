package com.example.search_key

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.search_key.bookmark.BookmarkFragment
import com.example.search_key.databinding.ActivityMainBinding
import com.example.search_key.model.SearchItemModel
import com.example.search_key.search.SearchFragment

class MainActivity : AppCompatActivity() {

    // ActivityMainBinding은 MainActivity의 레이아웃 바인딩 객체이다.
    private lateinit var binding: ActivityMainBinding

    // 좋아요를 눌러 선택된 아이템을 저장하는 리스트 - 공유 저장소
    var likedItems: ArrayList<SearchItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ActivityMainBinding 객체를 초기화하고 이를 사용하여 레이아웃 설정하기
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //버튼에 클릭 리스너를 설정해 각 프레그먼트 간의 전환하기
        binding.run {
            btnSearchFragment.setOnClickListener {
                Log.d("searchFragment","hi")
                setFragment(SearchFragment())
            }
            btnBookmarkFragment.setOnClickListener {
                Log.d("bookmarkFragment","hiiiiiiiiii")
                setFragment(BookmarkFragment())
            }
        }

        //앱 시작 시 기본적으로 SearchFragment 표시하기
        setFragment(SearchFragment())
    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    // 좋아요가 눌린 아이템을 likedItems 리스트에 추가하는 함수
    fun addLikedItem(item: SearchItemModel) {
        if (!likedItems.contains(item)) {
            likedItems.add(item)
        }
    }

    // 좋아요가 취소된 아이템을 likedItems 리스트에서 제거하는 함수
    fun removeLikedItem(item: SearchItemModel) {
        likedItems.remove(item)
    }
}