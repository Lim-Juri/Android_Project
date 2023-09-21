package com.example.search.retrofit

import android.content.Context
import android.util.Log
import com.example.search.retrofit.Constants.PREFS_NAME
import com.example.search.retrofit.Constants.PREF_KEY
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

// 추가 기능
object Utility {

    fun getDataTimeFormat(
        timestamp: String?,
        fromFormat: String?,
        toFormat: String?
    ): String {
    var date: Date? = null
        var result = ""
        try {
            val format = SimpleDateFormat(fromFormat)
            date = format.parse(timestamp)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        Log.d("date", "getDateFromTimestampWithFormat date >> $date")

        val dateFormat = SimpleDateFormat(toFormat)
        result = dateFormat.format(date)
        return result
    }

    fun saveLastSearch(context: Context, query: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(PREF_KEY, query).apply()
    }

    fun getLastSearch(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(PREF_KEY, null)
    }
}