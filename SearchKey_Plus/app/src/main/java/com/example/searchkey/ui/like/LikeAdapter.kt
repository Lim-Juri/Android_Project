package com.example.searchkey.ui.like

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchkey.Constants
import com.example.searchkey.data.model.SearchItemModel
import com.example.searchkey.databinding.SearchItemBinding
import com.example.searchkey.utils.Utils.getDateFromTimestampWithFormat

// 좋아요 리스트를 위한 RecyclerView의 어댑터
class LikeAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //표시될 항목들
    var items = mutableListOf<SearchItemModel>()

    //항목 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(item: SearchItemModel, position: Int)
    }

    private var clickListener: OnItemClickListener? = null

    //클릭 리스너 설정
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.clickListener = listener
    }

    //뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    // 항목에 데이터 바인딩
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 이미지 로딩
        Glide.with(mContext)
            .load(items[position].url)
            .into((holder as ItemViewHolder).iv_thum_image)

        // 항목 타입 설정 (이미지 or 비디오)
        var type = "[Image]"
        if (items[position].type == Constants.SEARCH_TYPE_VIDEO) type = "[Video]"

        holder.tv_title.text = type+items[position].title
        holder.iv_like.visibility = View.GONE
        holder.tv_datetime.text =
            getDateFromTimestampWithFormat(
                items[position].dateTime,
                "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
                "yyyy-MM-dd HH:mm:ss"
            )

        //항목 클릭 이벤트
        holder.cl_item.setOnClickListener {
            Log.d("LikeAdapter", "itemView onItemClick=${position}")
            clickListener?.onItemClick(items[position], position)
        }
    }

    // 항목 수 반환
    override fun getItemCount(): Int {
        return items.size
    }

    // 뷰 홀더 정의
    inner class ItemViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root) {

        var iv_thum_image: ImageView = binding.searchIvImage
        var iv_like: ImageView = binding.searchIvLike
        var tv_title: TextView = binding.searchTvTitle
        var tv_datetime: TextView = binding.searchTvDate
        var cl_item: ConstraintLayout = binding.constSearchItem

        init {
            // 좋아요 페이지에서는 '좋아요' 아이콘을 숨긴다.
            iv_like.visibility = View.GONE
        }
    }
}