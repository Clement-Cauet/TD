package com.unilasalle.td.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.unilasalle.td.services.database.DatabaseProvider
import com.unilasalle.td.services.database.entities.Quotes
import com.unilasalle.td.services.network.ApiService
import com.unilasalle.td.services.network.datas.Joke
import kotlinx.coroutines.launch

/**
 * Joke component
 *
 * This component displays a random joke
 */
@Composable
fun JokeComponent() {
    val apiService = ApiService.createJokeService()
    val context = LocalContext.current
    val databaseProvider = context as DatabaseProvider
    val quotesController = databaseProvider.database.quotesController()
    val coroutineScope = rememberCoroutineScope()

    var joke by remember { mutableStateOf<Joke?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    fun fetchJoke() {
        coroutineScope.launch {
            joke = apiService.getRandomJoke()
            isFavorite = false
        }
    }

    LaunchedEffect(Unit) {
        fetchJoke()
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { fetchJoke() }) {
                Text("Refresh")
            }
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Add to Favorites",
                tint = if (isFavorite) Color.Yellow else Color.White,
                modifier = Modifier.clickable {
                    isFavorite = true
                    joke?.let {
                        val newQuote = Quotes(text = it.setup, author = it.punchline)
                        coroutineScope.launch {
                            quotesController.insert(newQuote)
                        }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        joke?.let {
            Text(text = it.setup, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.punchline, style = MaterialTheme.typography.bodyLarge)
        } ?: run {
            CircularProgressIndicator()
        }
    }
}