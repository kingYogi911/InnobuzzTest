package com.yogi.innobuzztest.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefencesManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    private var preferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val PREF_NAME = "sales"

    init {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun saveString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, "")
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }


    fun removeValue(key: String) {
        editor.remove(key)
        editor.commit()
    }

    companion object Keys {
        const val IS_LOGIN = "is_login"
        const val USER_NAME = "user_name"
        const val OWNER_NAME = "owner_name"
        const val USER_TYPE = "user_type"
    }
}