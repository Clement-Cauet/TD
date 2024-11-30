package com.unilasalle.td

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.unilasalle.td.services.database.AppDatabase
import com.unilasalle.td.services.database.DatabaseProvider
import com.unilasalle.td.ui.components.QuoteListComponent
import com.unilasalle.td.ui.theme.TDTheme

class ListActivity : ComponentActivity(), DatabaseProvider {

    override val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        QuoteListComponent()
                    }
                }
            }
        }
    }
}