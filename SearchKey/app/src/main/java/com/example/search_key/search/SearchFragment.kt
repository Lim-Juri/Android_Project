package com.example.search_key.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.search_key.Constants
import com.example.search_key.Utils
import com.example.search_key.databinding.FragmentSearchBinding
import com.example.search_key.model.ImageModel
import com.example.search_key.model.SearchItemModel
import com.example.search_key.retrofit_client.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mContext: Context
    private lateinit var adapter: SearchAdapter

    // 아이템 높이에 따라 불규칙 적으로 타일 배치하기(불규칙 레이아웃) - StaggeredGridLayout
    private lateinit var gridmanager: StaggeredGridLayoutManager

    private var resItems: ArrayList<SearchItemModel> = ArrayList()

    //Fragment = Activity에서 context를 가져올 때 getActivity()보다 onAttach()의 인자로 넘겨진 context를 사용하는 것이 더 안전함
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupView() // 뷰 초기 설정
        setupListener() // 리스너 설정

        // Fragment layout 뷰 반환
        return binding.root
    }

    // 화면 뷰들의 초기 설정을 하는 메서드
    private fun setupView() {

        //RecyclerView 설정
        gridmanager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvSearchResult.layoutManager = gridmanager

        // Glide 때문에 Context 사용
        adapter = SearchAdapter(mContext)
        binding.rvSearchResult.adapter = adapter

        // 버그 해결을 위해 null 처리
        binding.rvSearchResult.itemAnimator = null

        // 최근 검색어를 가져와 EditText에 설정
        val lastSearch = Utils.getLastSearch(requireContext())
        binding.etSearch.setText(lastSearch)
    }

    // 화면에 있는 UI 요소들의 리스너 설정을 하는 메서드
    private fun setupListener() {

        binding.tvSearch.setOnClickListener {

            // query는 검색어
            val query = binding.etSearch.text.toString()
            if (query.isNotEmpty()) {
                //requireContext()를 통해 Context가 Null이 아님을 보장할 수 있다.
                Utils.saveLastSearch(requireContext(), query)
                adapter.clearItem()
                fetchImageResults(query)
            } else {
                Toast.makeText(mContext, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }

            // 검색 버튼을 눌렀을 때 키보드 밑으로 내리기
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // 키보드 숨기기
            imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        }
    }

    // 입력한 검색어로 이미지를 검색하는 메서드
    // 비동기식 - 호출하고 콜백을 기다림
    private fun fetchImageResults(query: String) {
        apiService.image_search(Constants.AUTH_HEADER, query, "recency", 1, 80)
            ?.enqueue(object : Callback<ImageModel?> {
                override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                    response.body()?.meta?.let { meta ->

                        // 검색 결과가 있는지 확인
                        if (meta.totalCount > 0 )  {
                            response.body()!!.documents.forEach { document ->
                                val title = document.displaySitename
                                val datetime = document.datetime
                                val url = document.thumbnailUrl
                                resItems.add(SearchItemModel(title, datetime, url))
                            }
                        }
                    }
                    adapter.items = resItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<ImageModel?>, t: Throwable) {
                    Log.e("hi", "onFailure: ${t.message}")
                }
            })
    }
}