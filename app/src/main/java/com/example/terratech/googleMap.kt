package com.example.terratech

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.terratech.ui.theme.TerraTechTheme

class googleMap : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                val intentHome = Intent(this@googleMap, MainActivity::class.java)
                Row() {
                    IconButton(onClick = {
                        startActivity(intentHome)
                    }) {
                        Icon(Icons.Filled.Home, "home")
                    }
                    Button(onClick = {
                        finish()
                    }) {
                        Text("Back", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}