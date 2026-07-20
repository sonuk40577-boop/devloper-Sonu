package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KundliScreen() {
    var showChart by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Free Kundli") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        if (!showChart) {
            KundliForm(onSubmit = { showChart = true })
        } else {
            KundliChartResult(onBack = { showChart = false })
        }
    }
}

@Composable
fun KundliForm(onSubmit: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var tob by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Enter Birth Details", style = MaterialTheme.typography.titleLarge)
        
        OutlinedTextField(
            value = name, onValueChange = { name = it },
            label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth().testTag("kundli_name")
        )
        OutlinedTextField(
            value = dob, onValueChange = { dob = it },
            label = { Text("Date of Birth (DD/MM/YYYY)") }, modifier = Modifier.fillMaxWidth().testTag("kundli_dob")
        )
        OutlinedTextField(
            value = tob, onValueChange = { tob = it },
            label = { Text("Time of Birth (HH:MM AM/PM)") }, modifier = Modifier.fillMaxWidth().testTag("kundli_tob")
        )
        OutlinedTextField(
            value = place, onValueChange = { place = it },
            label = { Text("Place of Birth (City)") }, modifier = Modifier.fillMaxWidth().testTag("kundli_place")
        )

        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth().height(50.dp).testTag("generate_kundli_button")
        ) {
            Text("Generate Kundli")
        }
    }
}

@Composable
fun KundliChartResult(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Lagna (Ascendant) Chart", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        
        // North Indian Style Chart Drawing (Square with diagonals)
        Box(
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val stroke = Stroke(width = 4f)
                val color = Color.Gray

                // Outer Box
                drawRect(color = color, style = stroke)

                // Diagonals
                drawLine(color = color, start = Offset(0f, 0f), end = Offset(w, h), strokeWidth = 4f)
                drawLine(color = color, start = Offset(w, 0f), end = Offset(0f, h), strokeWidth = 4f)

                // Inner Rhombus
                val path = Path().apply {
                    moveTo(w / 2, 0f)
                    lineTo(w, h / 2)
                    lineTo(w / 2, h)
                    lineTo(0f, h / 2)
                    close()
                }
                drawPath(path = path, color = color, style = stroke)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Note: This is a placeholder chart for the MVP preview. Real Kundli generation requires backend microservices.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
    }
}
