package com.example.bookmarkkk

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class EmailStoreModule(private val context: Context) {
    private val Context.datastore by preferencesDataStore(name = "datastore")

    private val stringKey = stringPreferencesKey("USER_EMAIL")

    val email : Flow<String> = context.datastore.data
        .catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {
            it[stringKey] ?: ""
        }

    suspend fun setEmail(email : String){
        context.datastore.edit {
            it[stringKey] = email
        }
    }

}