package com.esblock.app

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val sp: SharedPreferences =
        context.applicationContext.getSharedPreferences("esblock", Context.MODE_PRIVATE)

    var blockInstagram: Boolean
        get() = sp.getBoolean(KEY_INSTAGRAM, true)
        set(value) = sp.edit().putBoolean(KEY_INSTAGRAM, value).apply()

    var blockYoutube: Boolean
        get() = sp.getBoolean(KEY_YOUTUBE, true)
        set(value) = sp.edit().putBoolean(KEY_YOUTUBE, value).apply()

    var blockedCount: Int
        get() = sp.getInt(KEY_COUNT, 0)
        set(value) = sp.edit().putInt(KEY_COUNT, value).apply()

    private companion object {
        const val KEY_INSTAGRAM = "block_instagram"
        const val KEY_YOUTUBE = "block_youtube"
        const val KEY_COUNT = "blocked_count"
    }
}
