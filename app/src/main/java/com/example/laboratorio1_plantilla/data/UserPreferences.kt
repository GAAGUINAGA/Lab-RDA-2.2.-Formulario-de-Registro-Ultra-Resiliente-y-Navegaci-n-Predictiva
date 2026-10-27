package com.example.laboratorio1_plantilla.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Definición de la extensión de DataStore (fuera de la clase)
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    // Definición de llaves
    private companion object {
        val NAME_KEY = stringPreferencesKey("user_name")
    }

    // Flujo de datos para leer el nombre con manejo de errores
    val userName: Flow<String> = context.dataStore.data
        .catch { exception ->
            // Manejar errores de lectura de disco
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Retorna el valor o un string vacío por defecto
            preferences[NAME_KEY] ?: ""
        }

    // Función para guardar el nombre
    suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
        }
    }
}