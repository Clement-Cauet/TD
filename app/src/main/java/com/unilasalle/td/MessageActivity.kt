package com.unilasalle.td

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unilasalle.td.services.database.AppDatabase
import com.unilasalle.td.services.database.DatabaseProvider
import com.unilasalle.td.ui.components.ImageComponent
import com.unilasalle.td.ui.components.JokeComponent
import com.unilasalle.td.ui.theme.TDTheme

class MessageActivity : ComponentActivity(), DatabaseProvider {

    override val database by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val message = intent.getStringExtra("MESSAGE")
        setContent {
            TDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        MessageScreen(message)
                        JokeComponent()
                        ImageComponent()
                    }
                }
            }
        }
    }
}

@Composable
fun MessageScreen(message: String?) {
    Column (
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = message ?: "No message")
    }
}