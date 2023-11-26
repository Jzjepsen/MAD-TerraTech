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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController




class listOfTerrariums : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                val intent = Intent(this@listOfTerrariums, overviewOfTerrarium::class.java)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxWidth() // This ensures the Surface takes the full width
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column() {
                        Spacer(Modifier.width(40.dp))

                        Text(
                            text = "TerraTech",
                            style = MaterialTheme.typography.displayLarge
                        )

                        Spacer(Modifier.width(15.dp))

                        Text("Below is your list of Terrariums:", style = MaterialTheme.typography.headlineSmall)

                        Spacer(Modifier.width(40.dp))

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
}

/*
@Composable
fun MyApp(content: @Composable () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
        content()
    }
}


@Composable
fun TerrariumList(navController: NavController) {
    Column {
        Button(
            onClick = { navController.navigate("terrarium1") },
            modifier = Modifier.height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Text("Terrarium 1", fontSize = 18.sp, color = Color.Blue)
        }
        Button(
            onClick = { navController.navigate("terrarium1") },
            modifier = Modifier.height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Text("Terrarium 2", fontSize = 18.sp, color = Color.Blue)
        }
        Button(
            onClick = { navController.navigate("terrarium1") },
            modifier = Modifier.height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Text("Terrarium 3", fontSize = 18.sp, color = Color.Blue)
        }
        Button(
            onClick = { navController.navigate("terrarium1") },
            modifier = Modifier.height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),
            contentPadding = PaddingValues(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Text("Terrarium 4", fontSize = 18.sp, color = Color.Blue)
        }
    }
}

*/