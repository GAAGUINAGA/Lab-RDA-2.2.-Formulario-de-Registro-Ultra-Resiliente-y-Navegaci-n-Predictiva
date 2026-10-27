package com.example.laboratorio1_plantilla

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.laboratorio1_plantilla.data.UserPreferences
import com.example.laboratorio1_plantilla.ui.theme.FormViewModel
import com.example.laboratorio1_plantilla.ui.theme.Laboratorio1_PlantillaTheme

class MainActivity : ComponentActivity() {
    private val formViewModel: FormViewModel by viewModels {
        viewModelFactory {
            initializer {
                FormViewModel(createSavedStateHandle(), UserPreferences(applicationContext))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Laboratorio1_PlantillaTheme {
                ResilientFormScreen(formViewModel)
            }
        }
    }
}

@Composable
fun ResilientFormScreen(viewModel: FormViewModel) {
    val nameDisk by viewModel.nameFromDisk.observeAsState("")
    var nameInput by rememberSaveable { mutableStateOf<String?>(null) }
    val currentName = nameInput ?: nameDisk

    BackHandler(enabled = currentName.isNotEmpty() || viewModel.email.isNotEmpty()) {
        Log.d("NAV", "El usuario intentó retroceder con datos en el formulario")
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Borrador de Perfil", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = currentName,
            onValueChange = {
                nameInput = it
                viewModel.saveName(it)
            },
            label = { Text("Nombre (Persiste al cerrar app)") }
        )
        if (viewModel.nameSaved) {
            Text("✓")
        }

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email (Persiste al rotar/muerte proceso)") }
        )

    }
}
