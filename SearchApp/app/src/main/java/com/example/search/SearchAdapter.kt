package com.example.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.search.data.KakaoImage
import com.example.search.databinding.SearchItemBinding
import com.example.search.retrofit.Utility

class SearchAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

    var items = ArrayList<KakaoImage>()

    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]

        Glide.with(context)
            .load(currentItem.url)
            .into(holder.iv_thumb)

        if (currentItem.isLike)
            holder.iv_like.setImageResource(R.drawable.like_color)
        else
            holder.iv_like.setImageResource(R.drawable.like)

        holder.tv_title.text = currentItem.title
        holder.tv_date.text = Utility.getDataTimeFormat(
            currentItem.date,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }

    override fun getItemCount() =items.size

    inner class ItemViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var iv_thumb: ImageView = binding.searchIvImage
        var iv_like: ImageView = binding.searchIvLike
        var tv_title: TextView = binding.searchTvTitle
        var tv_date: TextView = binding.searchTvDate
        var cl_item: ConstraintLayout = binding.constSearchItem

        init {
            iv_thumb.setOnClickListener(this)
            cl_item.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            item.isLike = !item.isLike

            if (item.isLike) {
                (context as MainActivity).addLikedItem(item)
            } else {
                (context as MainActivity).removeLikedItem(item)
            }

            notifyItemChanged(position)
        }
    }
}