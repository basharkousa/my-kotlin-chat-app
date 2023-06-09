package com.bashar.mychatapp.src.data.local.datasources.dataStore.prefsdataStore

import androidx.datastore.preferences.core.booleanPreferencesKey

object PreferencesKeys {
    val IS_FIRST_LAUNCH = booleanPreferencesKey("IS_FIRST_LAUNCH")
//        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
}