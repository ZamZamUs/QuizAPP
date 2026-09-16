package com.example.pawal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pawal.data.local.entity.Category
import com.example.pawal.data.local.entity.Question
import com.example.pawal.ui.components.ModernFAB
import com.example.pawal.ui.components.StyledCard
import com.example.pawal.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    categoryId: Int,
    viewModel: QuizViewModel,
    onBackClick: () -> Unit,
    onAddQuestionClick: () -> Unit,
    onEditQuestionClick: (Int) -> Unit,
    onStartQuizClick: () -> Unit,
) {
    var category by remember { mutableStateOf<Category?>(null) }
    val questions by viewModel.getQuestionsByCategory(categoryId).collectAsState(initial = emptyList())
    var showDeleteConfirm by remember { mutableStateOf(value = false) }
    var showEditNameDialog by remember { mutableStateOf(value = false) }
    var newCategoryName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = category?.namaTopik ?: "Detail", 
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                            contentDescription = "Kembali",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { 
                            newCategoryName = category?.namaTopik ?: ""
                            showEditNameDialog = true 
                        },
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Nama")
                    }
                    IconButton(onClick = { showDeleteConfirm = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus Kategori")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {
                if (questions.isNotEmpty()) {
                    ExtendedFloatingActionButton(
                        onClick = onStartQuizClick,
                        icon = { Icon(Icons.Default.PlayArrow, contentDescription = null) },
                        text = { Text("MULAI QUIZ", fontWeight = FontWeight.Bold) },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(20.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                ModernFAB(onClick = onAddQuestionClick) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Soal")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "${questions.size} Soal Tersimpan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            if (questions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Belum ada soal.", 
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(questions) { question ->
                        QuestionItem(
                            question = question,
                            onEdit = { onEditQuestionClick(question.id) },
                            onDelete = { viewModel.deleteQuestion(question) }
                        )
                    }
                }
            }
        }
    }

    // Dialogs
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            shape = RoundedCornerShape(24.dp),
            title = { Text("HAPUS KATEGORI?", fontWeight = FontWeight.Bold) },
            text = { Text("Tindakan ini tidak dapat dibatalkan.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        category?.let { viewModel.deleteCategory(it) }
                        showDeleteConfirm = false
                        onBackClick()
                    }
                ) {
                    Text("HAPUS", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("BATAL")
                }
            }
        )
    }

    if (showEditNameDialog) {
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            shape = RoundedCornerShape(24.dp),
            title = { Text("EDIT NAMA", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = newCategoryName,
                    onValueChange = { newCategoryName = it },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    label = { Text("Nama Baru") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        category?.let { 
                            viewModel.updateCategory(it.copy(namaTopik = newCategoryName))
                        }
                        showEditNameDialog = false
                    }
                ) {
                    Text("SIMPAN", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditNameDialog = false }) {
                    Text("BATAL")
                }
            }
        )
    }

    // Get category name
    val categories by viewModel.categoriesWithCount.collectAsState(initial = emptyList())
    LaunchedEffect(categories) {
        category = categories.find { it.id == categoryId }?.let { 
            Category(id = it.id, namaTopik = it.namaTopik)
        }
    }
}

@Composable
fun QuestionItem(
    question: Question,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    StyledCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = question.teksSoal, 
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            OptionChip(label = "A", text = question.opsiA, isCorrect = question.kunciJawaban == "A")
            OptionChip(label = "B", text = question.opsiB, isCorrect = question.kunciJawaban == "B")
            OptionChip(label = "C", text = question.opsiC, isCorrect = question.kunciJawaban == "C")
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun OptionChip(label: String, text: String, isCorrect: Boolean) {
    Surface(
        color = if (isCorrect) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$label.", 
                fontWeight = FontWeight.Bold, 
                color = if (isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
