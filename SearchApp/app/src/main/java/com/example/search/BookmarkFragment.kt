package com.example.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.search.data.KakaoImage
import com.example.search.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    private lateinit var mContext: Context
    private var _binding: FragmentBookmarkBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var adapter: BookmarkAdapter
    private var likedItems: List<KakaoImage> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setItems()
    }

    private fun setItems() {
        val fetchlike = activity as MainActivity
        likedItems = fetchlike.likedItems
        if (::adapter.isInitialized) {
            adapter.items = fetchlike.likedItems
            adapter.notifyDataSetChanged()
        } else {
            adapter = BookmarkAdapter(mContext).apply {
                items = likedItems.toMutableList()
            }
            binding.bookmarkRecyclerview.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.bookmarkRecyclerview.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



