package com.example.a2411021praktikum1.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    title: String,
    initialMk: String = "",
    initialJudul: String = "",
    initialDeskripsi: String = "",
    initialDeadline: String = "",
    initialPrioritas: String = "Sedang",
    initialStatus: String = "Belum",
    onSave: (String, String, String, String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var mk by remember { mutableStateOf(initialMk) }
    var judul by remember { mutableStateOf(initialJudul) }
    var deskripsi by remember { mutableStateOf(initialDeskripsi) }
    var deadline by remember { mutableStateOf(initialDeadline) }
    var prioritas by remember { mutableStateOf(initialPrioritas) }
    var status by remember { mutableStateOf(initialStatus) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = mk, onValueChange = { mk = it }, label = { Text("Mata Kuliah") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = judul, onValueChange = { judul = it }, label = { Text("Judul Tugas") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = deskripsi, onValueChange = { deskripsi = it }, label = { Text("Deskripsi") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = deadline, onValueChange = { deadline = it }, label = { Text("Deadline") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = prioritas, onValueChange = { prioritas = it }, label = { Text("Prioritas") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status") }, modifier = Modifier.fillMaxWidth())

            Button(
                onClick = { onSave(mk, judul, deskripsi, deadline, prioritas, status) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan")
            }
        }
    }
}