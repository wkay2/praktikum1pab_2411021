package com.example.a2411021praktikum1.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a2411021praktikum1.data.local.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTaskScreen(
    task: Task,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") }
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
            Text(text = task.mataKuliah, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(text = task.judul, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Divider()
            Text(text = "Deskripsi: ${task.deskripsi}")
            Text(text = "Deadline: ${task.deadline}")
            Text(text = "Prioritas: ${task.prioritas}")
            Text(text = "Status: ${task.status}")
        }
    }
}