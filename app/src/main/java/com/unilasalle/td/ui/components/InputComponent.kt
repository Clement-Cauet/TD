package com.unilasalle.td.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.unilasalle.td.MessageActivity

/**
 * Input component
 *
 * This component displays an input field and a submit button
 *
 * @param context The context
 */
@Composable
fun InputComponent(context: Context) {
    var text by remember { mutableStateOf("") }

    Row (
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter text") }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {
            if (text.isEmpty()) {
                Toast.makeText(context, "Message is empty", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, MessageActivity::class.java)
                intent.putExtra("MESSAGE", text)
                context.startActivity(intent)
            }
        }) {
            Text("Submit")
        }
    }
}