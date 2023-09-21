package com.example.search_key.search

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

class SearchAdapter(private val mContext: Context) :
    RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

    var items = ArrayList<SearchItemModel>()

    // 아이템 목록을 초기화하는 메서드
    fun clearItem() {
        items.clear()
        // notifyDataSetChanged는 리스트의 크기와 아이템이 둘 다 변경되는 경우에 사용
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

        // Glide를 쓰려면 context가 필요함
        // Adapter에서는 context를 사용할 수 없음. 그래서 Fragment에서 넘겨줘야 함.
        Glide.with(mContext)
            .load(currentItem.url)
            .into(holder.iv_thumb_image)

        holder.iv_like.visibility = if (currentItem.isLike) View.VISIBLE else View.INVISIBLE
        holder.tv_title.text = currentItem.title
        holder.tv_datetime.text = getDataFromTimestampWithFormat(
            currentItem.dateTime,
            "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
            "yyyy-MM-dd HH:mm:ss"
        )
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(binding: SearchItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        var iv_thumb_image: ImageView = binding.ivThumbImage
        var iv_like: ImageView = binding.ivLike
        var tv_title: TextView = binding.tvTitle
        var tv_datetime: TextView = binding.tvDatetime
        var cl_thumb_item: ConstraintLayout = binding.clThumbItem

        init {
            iv_like.visibility = View.GONE
            iv_thumb_image.setOnClickListener(this)
            cl_thumb_item.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            item.isLike = !item.isLike

            if (item.isLike) {
                (mContext as MainActivity).addLikedItem(item) // 클릭하면 메인엑티비티에 아이템이 쌓임
            } else {
                (mContext as MainActivity).removeLikedItem(item)
            }

            notifyItemChanged(position)
        }
    }
}