package com.example.pawal.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pawal.data.local.entity.Question
import com.example.pawal.ui.components.ModernButton
import com.example.pawal.ui.components.ModernTextField
import com.example.pawal.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditQuestionScreen(
    categoryId: Int,
    questionId: Int?,
    viewModel: QuizViewModel,
    onBackClick: () -> Unit,
) {
    var teksSoal by remember { mutableStateOf("") }
    var opsiA by remember { mutableStateOf("") }
    var opsiB by remember { mutableStateOf("") }
    var opsiC by remember { mutableStateOf("") }
    var kunciJawaban by remember { mutableStateOf("A") }

    val questions by viewModel.getQuestionsByCategory(categoryId).collectAsState(initial = emptyList())
    
    LaunchedEffect(questionId, questions) {
        if (questionId != null) {
            val q = questions.find { it.id == questionId }
            if (q != null) {
                teksSoal = q.teksSoal
                opsiA = q.opsiA
                opsiB = q.opsiB
                opsiC = q.opsiC
                kunciJawaban = q.kunciJawaban
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = if (questionId == null) "Tambah Soal" else "Edit Soal",
                        fontWeight = FontWeight.Bold
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ModernTextField(
                value = teksSoal,
                onValueChange = { teksSoal = it },
                label = "Teks Soal",
                minLines = 3
            )

            ModernTextField(
                value = opsiA,
                onValueChange = { opsiA = it },
                label = "Opsi A"
            )

            ModernTextField(
                value = opsiB,
                onValueChange = { opsiB = it },
                label = "Opsi B"
            )

            ModernTextField(
                value = opsiC,
                onValueChange = { opsiC = it },
                label = "Opsi C"
            )

            Text(
                text = "KUNCI JAWABAN", 
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("A", "B", "C").forEach { option ->
                    ChoiceChip(
                        label = option,
                        isSelected = kunciJawaban == option,
                        onClick = { kunciJawaban = option },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ModernButton(
                onClick = {
                    if (teksSoal.isNotBlank() && opsiA.isNotBlank() && opsiB.isNotBlank() && opsiC.isNotBlank()) {
                        val newQuestion = Question(
                            id = questionId ?: 0,
                            categoryId = categoryId,
                            teksSoal = teksSoal,
                            opsiA = opsiA,
                            opsiB = opsiB,
                            opsiC = opsiC,
                            kunciJawaban = kunciJawaban
                        )
                        if (questionId == null) {
                            viewModel.addQuestion(newQuestion)
                        } else {
                            viewModel.updateQuestion(newQuestion)
                        }
                        onBackClick()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                text = "SIMPAN PERUBAHAN",
                enabled = teksSoal.isNotBlank() && opsiA.isNotBlank() && opsiB.isNotBlank() && opsiC.isNotBlank()
            )
        }
    }
}

@Composable
fun ChoiceChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
        modifier = modifier
    ) {
        Box(modifier = Modifier.padding(12.dp), contentAlignment = Alignment.Center) {
            Text(text = label, fontWeight = FontWeight.Bold)
        }
    }
}
