package com.example.terratech

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.terratech.ui.theme.TerraTechTheme

class manageTerrarium : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                val intentHome = Intent(this@manageTerrarium, MainActivity::class.java)
                Row() {
                    IconButton(onClick = {
                        startActivity(intentHome)
                    })
                    {
                        Icon(Icons.Filled.Home, "home")
                    }
                    Button(onClick = {
                        finish()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back")
                        Text("  Back", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}