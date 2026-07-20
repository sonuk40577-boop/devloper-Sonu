package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoroscopeScreen() {
    val signs = listOf(
        "Aries", "Taurus", "Gemini", "Cancer",
        "Leo", "Virgo", "Libra", "Scorpio",
        "Sagittarius", "Capricorn", "Aquarius", "Pisces"
    )

    var selectedSign by remember { mutableStateOf<String?>(null) }
    var language by remember { mutableStateOf("EN") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(if (language == "EN") "Daily Horoscope" else "दैनिक राशिफल") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            actions = {
                TextButton(onClick = { language = if (language == "EN") "HI" else "EN" }) {
                    Text(language, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(signs) { sign ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .aspectRatio(1f)
                        .clickable { selectedSign = sign },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = sign,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    if (selectedSign != null) {
        AlertDialog(
            onDismissRequest = { selectedSign = null },
            title = { Text("$selectedSign - Daily Reading") },
            text = { Text("Today brings clarity and focus for $selectedSign. Trust your instincts and avoid making rushed decisions. A pleasant surprise awaits you in the evening.\n\n(Simulated MVP Data)") },
            confirmButton = {
                TextButton(onClick = { selectedSign = null }) {
                    Text("OK")
                }
            }
        )
    }
}
