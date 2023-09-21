package com.example.search_key.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.search_key.MainActivity
import com.example.search_key.Utils.getDataFromTimestampWithFormat
import com.example.search_key.databinding.SearchItemBinding
import com.example.search_key.model.SearchItemModel

class BookmarkAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 북마크된 아이템들을 저장하는 리스트
    var items = mutableListOf<SearchItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 이미지 로딩 라이브러리(Glide)를 사용해 썸네일 이미지를 로드한다.
        Glide.with(mContext)
            .load(items[position].url)
            .into((holder as ItemViewHolder).iv_thumb_image)

        holder.tv_title.text = items[position].title
        holder.iv_like.visibility = View.GONE // 좋아요한 아이콘을 숨김
        holder.tv_datetime.text = getDataFromTimestampWithFormat(
            items[position].dateTime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var iv_thumb_image: ImageView = binding.ivThumbImage
        var iv_like: ImageView = binding.ivLike
        var tv_title: TextView = binding.tvTitle
        var tv_datetime: TextView = binding.tvDatetime
        var cl_item: ConstraintLayout = binding.clThumbItem

        init {
            // 북마크 페이지에서는 '좋아요' 아이콘을 숨김
            iv_like.visibility = View.GONE

            // 아이템 클릭 리스너 설정
            cl_item.setOnClickListener {
                val position = adapterPosition
                (mContext as MainActivity).removeLikedItem(items[position])
                if(position != RecyclerView.NO_POSITION) {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}