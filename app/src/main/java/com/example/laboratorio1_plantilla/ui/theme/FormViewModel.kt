package com.example.laboratorio1_plantilla.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.laboratorio1_plantilla.data.UserPreferences
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FormViewModel(
    private val stateHandle: SavedStateHandle,
    private val userPrefs: UserPreferences
) : ViewModel() {

    // Estado que sobrevive a Muerte del Proceso (Process Death)
    var email by mutableStateOf(stateHandle.get<String>("email_key") ?: "")
        private set
    fun updateEmail(newEmail: String) {
        email = newEmail
        stateHandle["email_key"] = newEmail
    }
    // Estado que sobrevive a Cierre de App (DataStore)
    val nameFromDisk = userPrefs.userName.asLiveData()
    var nameSaved by mutableStateOf(false)
        private set
    private var indicatorJob: Job? = null

    fun saveName(newName: String) {
        viewModelScope.launch {
            userPrefs.saveName(newName)
            nameSaved = true
            indicatorJob?.cancel()
            indicatorJob = viewModelScope.launch {
                delay(1000)
                nameSaved = false
            }
        }
    }
}
