package org.d3if0105.foodiefiends.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore : DataStore<Preferences> by preferencesDataStore(
    name = "settings_preferences"
)
class SettingsDataStore(private val context: Context) {

    companion object{
        private val IS_LIST= booleanPreferencesKey("is_list")
    }
    val layoutFlow: Flow<Boolean> = context.datastore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }
    suspend fun saveLayout(isList: Boolean) {
        context.datastore.edit { preferences ->
            preferences[IS_LIST] = isList

        }
    }


}