package com.example.search_key

import android.content.Context
import android.util.Log
import com.example.search_key.Constants.PREFS_NAME
import com.example.search_key.Constants.PREF_KEY
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

// 앱 전체에서 사용되는 유틸리티 함수들을 포함하는 객체

object Utils {

    // 주어진 timestamp를 원하는 포맷으로 변환하여 반환함 - 날짜, 시간을 새로운 형태로 바꿔주기
    fun getDataFromTimestampWithFormat(
        timestamp: String?, // 원본 timestamp 문자열
        fromFormatFormat: String?, // 원본 timestamp의 포맷
        toFormatformat: String? // 반환하고 싶은 날짜 포맷
    ): String {
        // 초기화 작업
        var date: Date? = null
        var res = ""
        try {
            val format = SimpleDateFormat(fromFormatFormat)
            // parse = 텍스트를 읽어서 해당 텍스트를 컴퓨터가 이해할 수 있는 형식으로 변환하는 작업
            date = format.parse(timestamp)
        } catch (e: ParseException) {
            // 오류 잡기용
            e.printStackTrace()
        }

        Log.d("date", "getDateFromTimestampWithFormat date >> $date")

        val dateFormat = SimpleDateFormat(toFormatformat)
        res = dateFormat.format(date)
        return res
    }

    // Preference = 사용자 설정 및 상태 정보를 저장하고 관리하는 데 사용되는 데이터 저장 메커니즘
    // 마지막 검색어를 Shared Preferences에 저장
    fun saveLastSearch(context: Context, query: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(PREF_KEY, query).apply()
    }

    // Shared Preferences에서 마지막 검색어 가져오기
    fun getLastSearch(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PREF_KEY, null)
    }
}