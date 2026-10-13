package com.example.a2411021praktikum1.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a2411021praktikum1.data.local.Task
import com.example.a2411021praktikum1.viewmodel.TaskViewModel

@Composable
fun AppNavHost(viewModel: TaskViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "task_list") {
        composable("task_list") {
            TaskListScreen(
                viewModel = viewModel,
                onAddTaskClick = { navController.navigate("add_task") },
                onTaskClick = { taskId -> navController.navigate("detail_task/$taskId") }
            )
        }
        composable("add_task") {
            AddEditTaskScreen(
                title = "Tambah Tugas",
                onSave = { mk, judul, deskripsi, deadline, prioritas, status ->
                    viewModel.addTask(mk, judul, deskripsi, deadline, prioritas, status)
                    navController.popBackStack()
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "detail_task/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val tasks by viewModel.allTasks.collectAsState()
            val task = tasks.find { it.id == taskId }

            if (task != null) {
                DetailTaskScreen(
                    task = task,
                    onEditClick = { navController.navigate("edit_task/$taskId") },
                    onDeleteClick = {
                        viewModel.deleteTask(task)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = "edit_task/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: 0
            val tasks by viewModel.allTasks.collectAsState()
            val task = tasks.find { it.id == taskId }

            if (task != null) {
                AddEditTaskScreen(
                    title = "Edit Tugas",
                    initialMk = task.mataKuliah,
                    initialJudul = task.judul,
                    initialDeskripsi = task.deskripsi,
                    initialDeadline = task.deadline,
                    initialPrioritas = task.prioritas,
                    initialStatus = task.status,
                    onSave = { mk, judul, deskripsi, deadline, prioritas, status ->
                        val updatedTask = task.copy(
                            mataKuliah = mk,
                            judul = judul,
                            deskripsi = deskripsi,
                            deadline = deadline,
                            prioritas = prioritas,
                            status = status
                        )
                        viewModel.updateTask(updatedTask)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddTaskClick: () -> Unit,
    onTaskClick: (Int) -> Unit
) {
    val tasks by viewModel.allTasks.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Manajemen Tugas Mahasiswa") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTaskClick) {
                Text("+", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Wahyu Krisadriyanto (2411021)",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (tasks.isEmpty()) {
                Text("Belum ada tugas tersimpan.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(tasks) { task ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onTaskClick(task.id) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = task.mataKuliah, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                Text(text = task.judul, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Status: ${task.status} | Deadline: ${task.deadline}", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

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
                title = { Text(title) },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Kembali", fontWeight = FontWeight.Bold)
                    }
                }
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
                title = { Text("Detail Tugas") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Kembali", fontWeight = FontWeight.Bold)
                    }
                },
                actions = {
                    TextButton(onClick = onEditClick) { Text("Edit") }
                    TextButton(onClick = onDeleteClick, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                        Text("Hapus")
                    }
                }
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