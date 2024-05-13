package com.example.ejerciciosavanzadosjetpackcompose.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.ejerciciosavanzadosjetpackcompose.ui.theme.entities.agenda.AgendaViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppAgenda(navController: NavController, viewModel: AgendaViewModel){
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Agenda de contactos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })

        },
        bottomBar = {
            BottomAppBar { }
        }
    ) {

        ContenidoCentral(navController = navController, viewModel = viewModel)

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactosList(viewModel: AgendaViewModel) {
    val context = LocalContext.current
    val contactos = viewModel.leerContactos(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contactos") }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (contactos.isNotEmpty()) {
                contactos.forEach { contacto ->
                    Text(text = "${contacto.nombre}: ${contacto.telefono}")
                }
            } else {
                Text(text = "No hay contactos guardados", modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContenidoCentral(navController: NavController,viewModel: AgendaViewModel) {
    var nombre by remember { mutableStateOf(TextFieldValue()) }
    var telefono by remember { mutableStateOf(TextFieldValue()) }
    var showDialog by remember { mutableStateOf(false) }
    var showContactosDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") }
        )
        TextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") }
        )
        Button(
            onClick = {
                viewModel.guardarContacto(context,nombre.text, telefono.text)
                showDialog = true
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Guardar Contacto")
        }

        Button(
            onClick = { showContactosDialog = true },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Mostrar Contactos")
        }
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(dismissOnClickOutside = false)
        ) {
            Surface(
                modifier = Modifier.padding(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Contacto Guardado!")
                    Button(
                        onClick = { showDialog = false },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }

    if (showContactosDialog) {
        Dialog(
            onDismissRequest = { showContactosDialog = false },
            properties = DialogProperties(dismissOnClickOutside = false)
        ) {
            Surface(
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp),
                elevation = 4.dp,

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        ContactosList(viewModel)
                    }
                    Row {
                        Button(
                            onClick = { showContactosDialog = false },
                            modifier = Modifier.padding(bottom = 50.dp)
                        ) {
                            Text("OK")
                        }
                    }
                }

            }
        }
    }
    }












