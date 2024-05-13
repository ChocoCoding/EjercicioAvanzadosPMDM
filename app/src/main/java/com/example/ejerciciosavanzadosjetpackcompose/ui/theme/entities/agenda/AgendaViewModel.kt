package com.example.ejerciciosavanzadosjetpackcompose.ui.theme.entities.agenda

import android.content.Context
import androidx.lifecycle.ViewModel
import java.io.File

class AgendaViewModel : ViewModel() {
    private val FILENAME = "contactoss.txt"
    val contactos = mutableListOf<Contacto>()
    fun guardarContacto(context: Context,nombre: String, telefono: String) {
        val file = File(context.filesDir, FILENAME)
        file.appendText("$nombre,$telefono\n")
    }

    fun leerContactos(context: Context): List<Contacto> {
        val file = File(context.filesDir, FILENAME)
        val contactosLeidos = mutableListOf<Contacto>()
        file.forEachLine { line ->
            val partes = line.split(",")
            if (partes.size == 2) {
                val nombre = partes[0]
                val telefono = partes[1]
                contactosLeidos.add(Contacto(nombre, telefono))
            }
        }
        return contactosLeidos
    }
}