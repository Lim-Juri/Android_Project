package com.example.search

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
import com.example.search.data.ImageSearchResponse
import com.example.search.data.KakaoImage
import com.example.search.databinding.FragmentSearchBinding
import com.example.search.retrofit.Constants
import com.example.search.retrofit.RetrofitClient.apiService
import com.example.search.retrofit.Utility
import retrofit2.Call
import retrofit2.Response


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mContext: Context
    private lateinit var adapter: SearchAdapter
    private lateinit var gridmanager: StaggeredGridLayoutManager

    private var resultItems: ArrayList<KakaoImage> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupView()
        setupListener()

        return binding.root
    }

    private fun setupView() {
        gridmanager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.searchRecyclerview.layoutManager = gridmanager

        adapter = SearchAdapter(mContext)
        binding.searchRecyclerview.adapter = adapter
        binding.searchRecyclerview.itemAnimator = null

        val lastSearch = Utility.getLastSearch(requireContext())
        binding.fmEtSearch.setText(lastSearch)
    }

    private fun setupListener() {

        binding.fmEtSearch.setOnClickListener {

            val query = binding.fmEtSearch.text.toString()
            if (query.isNotEmpty()) {
                Utility.saveLastSearch(requireContext(), query)
                adapter.clearItem()
                fetchImageResults(query)
            } else {
                Toast.makeText(mContext, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }

            val keyboard =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(binding.fmEtSearch.windowToken, 0)
        }
    }

    private fun fetchImageResults(query: String) {
        apiService.searchImage(Constants.AUTH_HEADER, query, "recency", 1, 80)
            ?.enqueue(object Callback<ImageSearchResponse?> {
                override fun onResponse(
                    call: Call<ImageSearchResponse?>,
                    response: Response<ImageSearchResponse?>
                ) {
                    response.body()?.metaData?.let { metaData ->
                        if (metaData.totalCount > 0) {
                            response.body()!!.documents.forEach { document ->
                                val title = document.displaySitename
                                val date = document.datetime
                                val url = document.thumbnailUrl
                                resultItems.add(KakaoImage(title, date, url))
                            }
                        }
                    }

                    adapter.items = resultItems
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<KakaoImage?>, t: Throwable) {
                    Log.e("error", "onFailure: ${t.message}")
                }
            })
    }
}
