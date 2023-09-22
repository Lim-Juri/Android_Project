package com.example.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.search.data.KakaoImage
import com.example.search.databinding.SearchItemBinding
import com.example.search.retrofit.Utility.getDataTimeFormat
import java.lang.Exception

class BookmarkAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 좋아요 저장
    var items = mutableListOf<KakaoImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(mContext)
            .load(items[position].url)
            .into((holder as ItemViewHolder).iv_thumb)

        holder.tv_title.text = items[position].title
        holder.iv_like.setImageResource(R.drawable.like_color)
        holder.tv_date.text = getDataTimeFormat(
            items[position].date, "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00", "yyyy-MM-dd HH:mm:ss"
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var iv_thumb: ImageView = binding.searchIvImage
        var iv_like: ImageView = binding.searchIvLike
        var tv_title: TextView = binding.searchTvTitle
        var tv_date: TextView = binding.searchTvDate
        var cl_item: ConstraintLayout = binding.constSearchItem

        init {
            cl_item.setOnClickListener {
                val position = adapterPosition
                (mContext as MainActivity).removeLikedItem(items[position])
                if (position != RecyclerView.NO_POSITION) {
                    try {
                        items.removeAt(position)
                    } catch (e:Exception){
                        Log.e("juri","items: $items,position: $position")
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }
}
