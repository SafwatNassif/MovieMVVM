package com.example.movieappstarter.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by Safwat Nassif on 7/24/2019.
 */

class SharedPrefUtils @Inject constructor(private val context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)

    private fun putString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    private fun getString(key: String) = pref.getString(key, "")

    private fun putInt(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }

    private fun getInt(key: String) = pref.getInt(key, 0)






    companion object {
        const val PREF_FILE = "shared_preference_file_store"
    }
}