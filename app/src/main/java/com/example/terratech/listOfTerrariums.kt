package com.example.terratech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.terratech.ui.theme.TerraTechTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController




class listOfTerrariums : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                MyApp {
                    TerrariumList(this)
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column() {
            Title(title = "TerraTech")

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Welcome to TerraTech, your smart terrarium assistant. ",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Click on a terrarium to view its data!",
                style = MaterialTheme.typography.headlineSmall
            )

            content()
        }
    }
}

@Composable
fun Title(title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth() // This ensures the Surface takes the full width
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth(), // Ensures Text takes full width of the Surface
            textAlign = TextAlign.Center // Centers the text
        )
    }
}

@Composable
fun TerrariumList(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Terrarium 1 Button
        Spacer(Modifier.height(15.dp))

        Button(
            onClick = {
                val intent = Intent(context, overviewOfTerrarium::class.java)
                intent.putExtra("number", "1")
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("Terrarium 1", fontSize = 24.sp, color = Color.White)
        }

        Spacer(Modifier.height(8.dp))

        // Terrarium 2 Button
        Button(
            onClick = {
                val intent = Intent(context, overviewOfTerrarium::class.java)
                intent.putExtra("number", "2")
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("Terrarium 2", fontSize = 24.sp, color = Color.White)
        }

        Spacer(Modifier.height(8.dp))

        // Terrarium 3 Button
        Button(
            onClick = {
                val intent = Intent(context, overviewOfTerrarium::class.java)
                intent.putExtra("number", "3")
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("Terrarium 3", fontSize = 24.sp, color = Color.White)
        }

        Spacer(Modifier.height(8.dp))

        // Terrarium 4 Button
        Button(
            onClick = {
                val intent = Intent(context, overviewOfTerrarium::class.java)
                intent.putExtra("number", "4")
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth() // Make the button fill the available width
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("Terrarium 4", fontSize = 24.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TerrariumListPreview() {
    TerrariumList(context = LocalContext.current)
}