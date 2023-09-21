package com.example.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.search.data.KakaoImage
import com.example.search.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    private lateinit var mContext: Context
    private var binding: FragmentBookmarkBinding? = null
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
    ): View? {
        val fetchlike = activity as MainActivity
        likedItems = fetchlike.likedItems

        Log.d("BookmarkFragment", "likedItems size = ${likedItems.size}")

        adapter = BookmarkAdapter(mContext).apply {
            items = likedItems.toMutableList()
        }

        adapter.notifyDataSetChanged()

        binding = FragmentBookmarkBinding.inflate(inflater, container, false).apply {
            bookmarkRecyclerview.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            bookmarkRecyclerview.adapter = adapter
        }

        return binding?.root
    }
}
