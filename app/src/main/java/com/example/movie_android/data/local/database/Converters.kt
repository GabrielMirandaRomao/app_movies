package com.example.movie_android.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromList(list: ArrayList<Int>?): Int? {
        return list?.get(0)
    }

    @TypeConverter
    fun toGenre(value: Int?): ArrayList<Int> {
        val listType = object : TypeToken<ArrayList<Int>>(){}.type
        return Gson().fromJson(value.toString(), listType)
    }
}