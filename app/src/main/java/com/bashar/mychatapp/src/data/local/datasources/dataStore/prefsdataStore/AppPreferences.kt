package com.bashar.mychatapp.src.data.local.datasources.dataStore.prefsdataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AppPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        const val APP_PREFERENCES = "app_preferences"
    }


//    fun isFirstLaunch(): Boolean = dataStore.data.map { it[PreferencesKeys.IS_FIRST_LAUNCH] ?: true  }

    fun isFirstLaunch(): Flow<Boolean> = dataStore.data.catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data

        if (exception is IOException) {
//            print("TheErrorHere")
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.IS_FIRST_LAUNCH] ?: true }

    suspend fun toggleIsFirstLaunch() {
//        dataStore.edit {
//            it[PreferencesKeys.IS_FIRST_LAUNCH] = !(it[PreferencesKeys.IS_FIRST_LAUNCH] ?: true)
//        }
        dataStore.edit {
            it[PreferencesKeys.IS_FIRST_LAUNCH] = false
        }
    }

//    val isDarkTheme : Flow<Boolean> = dataStore.data.map { preferences ->
//
//    }
//    suspend fun changeAppTheme(enable: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[PreferencesKeys.APP_THEME] = enable
//        }
//    }


}