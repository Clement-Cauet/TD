package com.unilasalle.td.ui.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.unilasalle.td.services.database.DatabaseProvider
import com.unilasalle.td.services.database.entities.Quotes
import kotlinx.coroutines.launch

/**
 * Quote list component
 *
 * This component displays a list of quotes
 */
@Composable
fun QuoteListComponent() {
    val context = LocalContext.current
    val databaseProvider = context as DatabaseProvider
    val quotesController = databaseProvider.database.quotesController()
    val coroutineScope = rememberCoroutineScope()

    var quotes by remember { mutableStateOf(listOf<Quotes>()) }

    LaunchedEffect(Unit) {
        quotes = quotesController.getAll()
    }
    fun deleteQuote(quote: Quotes) {
        coroutineScope.launch {
            quotesController.delete(quote)
            quotes = quotesController.getAll()
        }
    }

    LazyColumn (modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
        items(quotes) { quote ->
            QuoteItem(context, quote, onDelete = { deleteQuote(quote) })
        }

    }
}

/**
 * Quote item
 *
 * This component displays a single quote
 *
 * @param context The context
 * @param quote The quote
 * @param onDelete The function to call when the quote is deleted
 */
@Composable
fun QuoteItem(context: Context, quote: Quotes, onDelete: () -> Unit) {
    Row (
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Column (
            modifier = Modifier.weight(0.9f)
        ) {
            Text(text = quote.text)
            Text(text = quote.author)
        }
        Column (
            modifier = Modifier.weight(0.1f)
        ) {
            ShareButtonComponent(context, quote.text + "\n" + quote.author)
            DeleteButtonComponent(onDelete)
        }
    }
}

