package com.example.pawal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawal.ui.components.ModernButton
import com.example.pawal.ui.components.StyledCard
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    score: Int,
    correct: Int,
    total: Int,
    date: Long,
    onBackToCategory: () -> Unit,
    onRetryClick: () -> Unit,
) {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    val dateString = sdf.format(Date(date))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "HASIL AKHIR", 
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackToCategory) {
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
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SKOR ANDA",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 90.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            StyledCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ResultRow(label = "Benar", value = correct.toString(), color = MaterialTheme.colorScheme.primary)
                    ResultRow(label = "Salah", value = (total - correct).toString(), color = MaterialTheme.colorScheme.error)
                    ResultRow(label = "Total Soal", value = total.toString())
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    ResultRow(label = "Waktu", value = dateString)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            ModernButton(
                onClick = onRetryClick,
                modifier = Modifier.fillMaxWidth(),
                text = "ULANGI KUIS",
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(
                onClick = onBackToCategory,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("KEMBALI KE KATEGORI", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String, color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label.uppercase(), 
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value, 
            style = MaterialTheme.typography.bodyLarge, 
            fontWeight = FontWeight.Black,
            color = color
        )
    }
}
