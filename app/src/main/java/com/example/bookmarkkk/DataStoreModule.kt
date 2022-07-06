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
    private val pwKey = stringPreferencesKey("USER_PW")

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

    suspend fun setPassword(password : String){
        context.datastore.edit {
            it[pwKey] = password
        }
    }

}