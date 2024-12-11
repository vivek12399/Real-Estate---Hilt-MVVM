package com.example.property.utils

import android.content.Context
import com.example.property.utils.Constants.FIRST_TIME_USER
import com.example.property.utils.Constants.PREFSTOKENFILE
import com.example.property.utils.Constants.ROLE_ID
import com.example.property.utils.Constants.USER_ID
import com.example.property.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs  = context.getSharedPreferences(PREFSTOKENFILE,Context.MODE_PRIVATE)

    fun saveToken(token:String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN,token)
        editor.apply()
    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN,null)
    }
    fun saveUserId(token:String){
        val editor = prefs.edit()
        editor.putString(USER_ID,token)
        editor.apply()
    }

    fun getUSerId(): String? {
        return prefs.getString(USER_ID,null)
    }

    fun saveRole(token:String){
        val editor = prefs.edit()
        editor.putString(ROLE_ID,token)
        editor.apply()
    }

    fun getRole(): String? {
        return prefs.getString(ROLE_ID,null)
    }

    fun isFirstTimeInDashboard(isFirstTime: Boolean = true) {
        prefs.edit().putBoolean(FIRST_TIME_USER, isFirstTime).apply()
    }

    fun isFirstTime(): Boolean {
        return prefs.getBoolean(FIRST_TIME_USER, true)
    }
    fun clearToken() {
        val editor = prefs.edit()
        editor.remove(USER_TOKEN)
        editor.remove(ROLE_ID)

        editor.apply()
    }
}