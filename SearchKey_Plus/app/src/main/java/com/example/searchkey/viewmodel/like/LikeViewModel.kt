package com.example.searchkey.viewmodel.like

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchkey.data.model.SearchItemModel
import com.example.searchkey.utils.Utils

class LikeViewModel : ViewModel() {

    // 북마크된 아이템들에 대한 MutableLiveData 선언
    private val _likedItems = MutableLiveData<List<SearchItemModel>>()

    // 외부에서 관찰할 수 있도록 LiveData로 제공
    val likedItems: LiveData<List<SearchItemModel>> get() = _likedItems

    // 저장된 북마크 아이템들을 가져오는 함수
    fun getlikedItems(context: Context) {
        // Utils 클래스를 이용해 저장된 북마크를 가져와서 _likedItems에 저장
        _likedItems.value = Utils.getPrefLikeItems(context)
    }

    // 특정 아이템을 삭제하는 함수
    fun deleteItem(context: Context, item: SearchItemModel, position: Int) {

        // Utils 클래스를 이용해 아이템 삭제
        Utils.deletePrefItem(context, item.url)
        Log.d("likeViewModel", "deleteItem position = ${position}, url = ${item.url}")

        // 삭제된 아이템 정보를 반영하여 LiveData 업데이트
        _likedItems.value?.let { curentItems ->
            val updatedItems = curentItems.toMutableList()
            updatedItems.remove(item)
            _likedItems.value = updatedItems
        }
    }
}