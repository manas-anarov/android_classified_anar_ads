package com.example.anarads.utils

import android.content.Context



object GetSetData {

    fun getMySavedToken(context: Context): String {
        val preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val token_in_func = preferences.getString("token", "")
        return "Token $token_in_func"
    }


}
