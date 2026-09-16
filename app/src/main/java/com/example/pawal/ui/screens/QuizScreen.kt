package com.example.pawal.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pawal.ui.components.ModernButton
import com.example.pawal.ui.components.StyledCard
import com.example.pawal.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    categoryId: Int,
    viewModel: QuizViewModel,
    onBackClick: () -> Unit,
    onQuizFinish: (Int, Int, Int, Long) -> Unit,
) {
    val questions by viewModel.currentQuizQuestions.collectAsState()
    val currentIndex by viewModel.currentQuestionIndex.collectAsState()
    val answers by viewModel.userAnswers.collectAsState()
    val quizResult by viewModel.quizResult.collectAsState()

    var showExitDialog by remember { mutableStateOf(value = false) }

    LaunchedEffect(categoryId) {
        viewModel.startQuiz(categoryId)
    }

    LaunchedEffect(quizResult) {
        quizResult?.let {
            val total = questions.size
            var correct = 0
            questions.forEachIndexed { index, question ->
                if (answers[index] == question.kunciJawaban) correct++
            }
            onQuizFinish(it.skor, correct, total, it.tanggalMain)
        }
    }

    BackHandler {
        showExitDialog = true
    }

    if (questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }
    } else {
        val currentQuestion = questions[currentIndex]
        val selectedAnswer = answers[currentIndex]

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            text = "SESI KUIS", 
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = { showExitDialog = true }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                                contentDescription = "Keluar",
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
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / questions.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "PERTANYAAN ${currentIndex + 1} DARI ${questions.size}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                StyledCard(
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                ) {
                    Text(
                        text = currentQuestion.teksSoal,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(28.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                QuizOption(
                    label = "A",
                    text = currentQuestion.opsiA,
                    isSelected = selectedAnswer == "A",
                    onClick = { viewModel.submitAnswer("A") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                QuizOption(
                    label = "B",
                    text = currentQuestion.opsiB,
                    isSelected = selectedAnswer == "B",
                    onClick = { viewModel.submitAnswer("B") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                QuizOption(
                    label = "C",
                    text = currentQuestion.opsiC,
                    isSelected = selectedAnswer == "C",
                    onClick = { viewModel.submitAnswer("C") }
                )

                Spacer(modifier = Modifier.weight(1f))

                ModernButton(
                    onClick = {
                        if (currentIndex < (questions.size - 1)) {
                            viewModel.nextQuestion()
                        } else {
                            viewModel.finishQuiz(categoryId)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    text = if (currentIndex < (questions.size - 1)) "BERIKUTNYA" else "SELESAIKAN",
                    enabled = selectedAnswer != null,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            shape = RoundedCornerShape(24.dp),
            title = { Text("KELUAR KUIS?", fontWeight = FontWeight.Bold) },
            text = { Text("Progres kuis Anda saat ini tidak akan disimpan.") },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    onBackClick()
                }) {
                    Text("KELUAR", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("LANJUTKAN")
                }
            }
        )
    }
}

@Composable
fun QuizOption(
    label: String,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    StyledCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = label, 
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
