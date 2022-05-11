package com.example.bookmarkkk

import android.content.Context
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreModule(private val context: Context) {
    private val Context.datastore by preferencesDataStore(name = "datastore")

    private val emailKey = stringPreferencesKey("USER_EMAIL")
    private val loginTypeKey = intPreferencesKey("LOGIN_TYPE")
    private val refreshTokenKey = stringPreferencesKey("REFRESH_TOKEN")

    val email : Flow<String> = context.datastore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {
            it[emailKey] ?: ""
        }

    //일반 로그인 : 1, 로그인 X : 0
   val loginType : Flow<Int> = context.datastore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }.map {
            it[loginTypeKey]!!
        }

    val refreshToken : Flow<String> = context.datastore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {
            it[refreshTokenKey] ?: ""
        }

    suspend fun setEmail(email : String){
        context.datastore.edit {
            it[emailKey] = email
        }
    }

    suspend fun setLoginType(type : Int){
        context.datastore.edit {
            it[loginTypeKey] = type
        }
    }

    suspend fun setToken(value : String){
        context.datastore.edit {
            it[refreshTokenKey] = value
        }
    }

}