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
    private val bookmarkTypeKey = stringPreferencesKey("BOOKMARK_TYPE")
    private val pwKey = stringPreferencesKey("USER_PW")

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

    //list : "1", grid : "0"
    val bookmarkType : Flow<String> = context.datastore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {
            it[bookmarkTypeKey] ?: ""
        }

    val password : Flow<String> = context.datastore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {
            it[pwKey] ?: ""
        }

    suspend fun setEmail(email : String){
        context.datastore.edit {
            it[emailKey] = email
        }
    }

    suspend fun setBookmarkType(type : String){
        context.datastore.edit {
            it[bookmarkTypeKey] = type
        }
    }

    suspend fun setPassword(password : String){
        context.datastore.edit {
            it[pwKey] = password
        }
    }

}