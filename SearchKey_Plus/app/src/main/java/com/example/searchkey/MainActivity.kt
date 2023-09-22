package com.example.searchkey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.searchkey.databinding.ActivityMainBinding


// MainActivity에서는 사용자의 주 탐색 인터페이스인 BottomNavigationView와 Navigation Component를 설정하고 연결한다.

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ActivityMainBinding 객체 초기화
        // layoutInflater를 사용해 activity_main.xml 레이아웃을 인플레이트한다.
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 초기화된 바인딩 객체의 root 뷰를 활동의 콘텐츠 뷰로 설정한다.
        setContentView(binding.root)

        // R.id.nav_host_fragment_activity_main ID를 가진 NavHostFragment의 NavController를 가져온다.
        // NavController는 Navigation Component에서 프래그먼트 간의 탐색을 관리한다.
        val navController = findNavController(R.id.fragment_activity_main)

        // BottomNavigationView를 NavController와 연결한다.
        // 이를 통해 탐색 메뉴 항목이 클릭될 때 적절한 프래그먼트로 자동 탐색된다.
        NavigationUI.setupWithNavController(binding.navigationView,navController)
    }
}