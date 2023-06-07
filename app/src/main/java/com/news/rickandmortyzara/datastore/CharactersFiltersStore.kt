package com.news.rickandmortyzara.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersFiltersStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun setFilterStatus(status: String) {
        dataStore.edit { preferences ->
            preferences[FILTER_STATUS] = status
        }
    }

    val filterStatus = dataStore.data.map { preferences ->
        preferences[FILTER_STATUS] ?: ""
    }


    companion object {
        val FILTER_STATUS = stringPreferencesKey("filter_status")
    }
}