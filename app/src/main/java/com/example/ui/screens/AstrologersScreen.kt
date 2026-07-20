package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Astrologer(val name: String, val specialties: String, val rate: Int, val rating: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstrologersScreen() {
    val astrologers = listOf(
        Astrologer("Acharya Sharma", "Vedic, Vastu", 15, 4.9),
        Astrologer("Tarot Priya", "Tarot, Numerology", 20, 4.8),
        Astrologer("Guru Ji", "Vedic, Kundli Milan", 25, 4.7),
        Astrologer("Astro Amit", "KP Astrology", 10, 4.5)
    )
    
    var connectingTo by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Consult Astrologers") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(astrologers) { astro ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(60.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(astro.name.first().toString(), style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(astro.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(astro.specialties, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Star, contentDescription = "Rating", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(astro.rating.toString(), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Button(
                            onClick = {
                                connectingTo = astro.name
                                scope.launch {
                                    delay(2000)
                                    connectingTo = null
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Chat, contentDescription = "Chat", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("₹${astro.rate}/min")
                        }
                    }
                }
            }
        }
    }

    if (connectingTo != null) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Connecting...") },
            text = { 
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Starting chat with $connectingTo. Please wait.")
                }
            },
            confirmButton = {}
        )
    }
}
