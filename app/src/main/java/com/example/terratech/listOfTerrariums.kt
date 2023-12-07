package com.example.terratech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.tooling.preview.Preview
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.terratech.ui.theme.TerraTechTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.terratech.data.Terrarium
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONArray
import java.io.IOException


class listOfTerrariums : ComponentActivity() {
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()
        val jsonData = readJSONFromAsset(this, "terrariums.json")
        val terrariums = parseTerrariums(jsonData)
        val myContext: Context = this
        addTerrariumsToFirestore(terrariums)
        setContent {
            TerraTechTheme {
                MyApp {
                    TerrariumList(terrariums)
                }
            }
        }
    }
    private fun addTerrariumsToFirestore(terrariums: List<Terrarium>) {
        terrariums.forEach { terrarium ->
            db.collection("terrariums").document(terrarium.id)
                .set(terrarium)
                .addOnSuccessListener { Log.d("Firestore", "Terrarium successfully written!") }
                .addOnFailureListener { e -> Log.w("Firestore", "Error writing terrarium", e) }
        }
    }
}
private fun readJSONFromAsset(context: Context, filename: String): String {
    return try {
        context.assets.open(filename).bufferedReader().use { it.readText() }
    } catch (ex: IOException) {
        ex.printStackTrace()
        ""
    }
}

private fun parseTerrariums(jsonData: String): List<Terrarium> {
    val jsonArray = JSONArray(jsonData)
    val terrariums = mutableListOf<Terrarium>()
    for (i in 0 until jsonArray.length()) {
        val jsonObject = jsonArray.getJSONObject(i)
        terrariums.add(Terrarium(
            id = jsonObject.getString("id"),
            name = jsonObject.getString("name"),
            temperature = jsonObject.getInt("temperature"),
            humidity = jsonObject.getInt("humidity"),
            plants = jsonObject.getJSONArray("plants").let { 0.until(it.length()).map { idx -> it.getString(idx) } }
        ))
    }
    return terrariums
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
fun TerrariumList(terrariums: List<Terrarium>) {
    // Retrieve the current context
    val currentContext = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        terrariums.forEach { terrarium ->
            Button(
                onClick = {
                    val intent = Intent(currentContext, overviewOfTerrarium::class.java)
                    intent.putExtra("name", terrarium.name)
                    currentContext.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Text(terrarium.name, fontSize = 24.sp, color = Color.White)
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TerrariumListPreview() {
    // Sample terrarium data for preview
    val sampleTerrariums = listOf(
        Terrarium("1", "Tropical Paradise", 28, 75, listOf("Fern", "Moss")),
        Terrarium("2", "Desert Oasis", 34, 20, listOf("Cactus", "Aloe Vera"))
        // Add more sample data as needed
    )
    TerrariumList(sampleTerrariums)
}