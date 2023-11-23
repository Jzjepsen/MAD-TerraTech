package com.example.terratech

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.terratech.ui.theme.TerraTechTheme

class overviewOfTerrarium : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                val intentHome = Intent(this@overviewOfTerrarium, MainActivity::class.java)
                val intentGoogle = Intent(this@overviewOfTerrarium, googleMap::class.java)
                val intentManage = Intent(this@overviewOfTerrarium, manageTerrarium::class.java)
                Column() {
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
                    Button(onClick = {
                        startActivity(intentGoogle)
                    }) {
                        Text("View this Terrarium on Google Map", style = MaterialTheme.typography.bodyLarge)
                    }
                    Button(onClick = {
                        startActivity(intentManage)
                    }) {
                        Text("Manage this Terrarium", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}