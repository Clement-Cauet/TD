package com.unilasalle.td

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.unilasalle.td.services.database.AppDatabase
import com.unilasalle.td.services.database.DatabaseProvider
import com.unilasalle.td.ui.components.CounterComponent
import com.unilasalle.td.ui.components.InputComponent
import com.unilasalle.td.ui.components.UserListComponent
import com.unilasalle.td.ui.theme.TDTheme

class MainActivity : ComponentActivity(), DatabaseProvider {

    override val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Button(onClick = {
                            startActivity(Intent(this@MainActivity, ListActivity::class.java))
                        }) {
                            Text("Open List Activity")
                        }
                        InputComponent(context = this@MainActivity)
                        CounterComponent()
                        UserListComponent()
                    }
                }
            }
        }
    }
}