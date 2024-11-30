package com.unilasalle.td.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Counter component
 *
 * This component displays a counter
 */
@Composable
fun CounterComponent() {
    var counter by remember { mutableIntStateOf(0) }

    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Counter: $counter")
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { counter++ }) {
            Text("+")
        }
    }
}