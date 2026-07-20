package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen() {
    var balance by remember { mutableStateOf(50) }
    var showPaymentIntent by remember { mutableStateOf(false) }
    var selectedAmount by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Wallet") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Available Balance", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("₹$balance", style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Recharge Wallet", style = MaterialTheme.typography.titleLarge, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(16.dp))

            val amounts = listOf(100, 500, 1000, 2000)
            
            // 2x2 Grid for amounts
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    RechargeButton(amounts[0], Modifier.weight(1f)) { selectedAmount = it; showPaymentIntent = true }
                    RechargeButton(amounts[1], Modifier.weight(1f)) { selectedAmount = it; showPaymentIntent = true }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    RechargeButton(amounts[2], Modifier.weight(1f)) { selectedAmount = it; showPaymentIntent = true }
                    RechargeButton(amounts[3], Modifier.weight(1f)) { selectedAmount = it; showPaymentIntent = true }
                }
            }
        }
    }

    if (showPaymentIntent) {
        AlertDialog(
            onDismissRequest = { showPaymentIntent = false },
            title = { Text("Razorpay/UPI Intent") },
            text = { Text("Simulating payment of ₹$selectedAmount via UPI...\n(This is a mock payment for the MVP preview)") },
            confirmButton = {
                TextButton(
                    onClick = {
                        balance += selectedAmount
                        showPaymentIntent = false
                    }
                ) {
                    Text("Simulate Success")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPaymentIntent = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun RechargeButton(amount: Int, modifier: Modifier = Modifier, onClick: (Int) -> Unit) {
    OutlinedButton(
        onClick = { onClick(amount) },
        modifier = modifier.height(60.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("₹$amount", style = MaterialTheme.typography.titleMedium)
    }
}
