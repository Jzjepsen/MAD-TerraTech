package com.example.terratech

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

class listOfTerrariums : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                val intent = Intent(this@listOfTerrariums, overviewOfTerrarium::class.java)
                Column() {
                    Button(onClick = {
                        startActivity(intent)
                    }) {
                        Text("View this Terrarium", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}
