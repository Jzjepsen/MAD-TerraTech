package com.example.terratech

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.terratech.ui.theme.TerraTechTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import javax.net.ssl.*


// Retrofit setup
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:3100/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// Service interface for the API
interface LocalWeatherService {
    @GET("/")
    suspend fun getWeatherData(): Response<ApiResponse>
}



// Data classes to match the JSON structure of the IoT endpoint
data class ApiResponse(
    val _id: String,
    val deviceId: String,
    val timestamp: String,
    val temperature: Double,
    val humidity: Int
)
data class WeatherData(
    val city: String,
    val current_weather: String,
    val temp: String,  // Assuming the temperature is a string
    val humidity: String  // Assuming the humidity is a string
)

// Data class for the terrarium data
data class TerrariumData(val temperature: Int, val humidity: Int)


// ViewModel to handle the logic and data processing
class TerrariumViewModel(private val service: LocalWeatherService) : ViewModel() {
    val terrariumData = MutableStateFlow(TerrariumData(0, 0))

    init {
        fetchTerrariumData()
    }

    fun refreshData() {

        fetchTerrariumData()

    }

    private fun fetchTerrariumData() {
        Log.d("test", "we are here")
        viewModelScope.launch {
            try {
                val response = service.getWeatherData()
                Log.d("test", "Response code: ${response.code()}")
                Log.d("test", "Response body: ${response.body()}")

                if (response.isSuccessful && response.body() != null) {
                    val weatherData = response.body()!!
                    val temperature = weatherData.temperature.toInt()
                    val humidity = weatherData.humidity.toInt()

                    terrariumData.value = TerrariumData(temperature, humidity)
                    Log.d("test", "data: $temperature")
                }
            } catch (e: Exception) {
                Log.e("TerrariumViewModel", "Error fetching data: ${e.message}", e)
            }
        }
    }

}


// Composable function to display the header
@Composable
fun PageHeader(title: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth() // This ensures the Surface takes the full width
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(), // Ensures Text takes full width of the Surface
            textAlign = TextAlign.Center // Centers the text
        )
    }
}

@Composable
fun DisplayData(degrees: String, humidity: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ) // Customize the box appearance
            .padding(16.dp) // Padding inside the box
    ) {
        Column {
            Text(
                text = "Degrees: $degrees°C",
                style = TextStyle(
                    fontSize = 18.sp, // Larger font size
                    fontWeight = FontWeight.Bold // Optional: make it bold
                )
            )
            Text(
                text = "Humidity: $humidity%",
                style = TextStyle(
                    fontSize = 18.sp, // Larger font size
                    fontWeight = FontWeight.Bold // Optional: make it bold
                )
            )
        }
    }
}

class TerrariumViewModelFactory(private val service: LocalWeatherService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TerrariumViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TerrariumViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


// Main screen Composable function
@Composable
fun TerrariumDetailsScreen(title: String,
    viewModel: TerrariumViewModel,
    onManageTerrariumClicked: () -> Unit,
    onGoogleMapClicked: () -> Unit
) {
    val terrariumData by viewModel.terrariumData.collectAsState()

    Column {
        PageHeader(title = title)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(15.dp))

            DisplayData(
                degrees = terrariumData.temperature.toString(),
                humidity = terrariumData.humidity.toString()
            )

            Spacer(Modifier.width(40.dp))


            Button(onClick = { viewModel.refreshData() }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Refresh"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Refresh")
            }
        }
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(15.dp))

            Button(onClick = onManageTerrariumClicked) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Icon Description"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Manage Terrarium")
            }
            Spacer(Modifier.width(40.dp)) // Add spacing here

            Button(onClick = onGoogleMapClicked) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Icon Description"
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Location")
            }
        }
        Spacer(Modifier.width(40.dp)) // Add spacing here

        Image(
            painter = painterResource(id = R.drawable.gecko),
            contentDescription = "Terrarium Image",
            modifier = Modifier
                .height(330.dp) // Specify the height of the image
                .fillMaxWidth() // Make the image fill the maximum width
                .padding(16.dp) // Add some padding
        )
    }
}

class overviewOfTerrarium : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3100/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(LocalWeatherService::class.java)
        val viewModelFactory = TerrariumViewModelFactory(service)

        setContent {
            TerraTechTheme {
                val intentHome = Intent(this@overviewOfTerrarium, listOfTerrariums::class.java)
                val number = intent.getStringExtra("number")
                val name = "Terrarium $number"

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
                                Icon(Icons.Filled.ArrowBack, "back")
                                Text("  Back", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        val viewModel: TerrariumViewModel = viewModel(factory = viewModelFactory)
                        TerrariumDetailsScreen(
                            name,
                            viewModel,
                            onManageTerrariumClicked = { navigateToManageTerrarium(name) },
                            onGoogleMapClicked = { navigateToGoogleMap() }
                        )
                    }
                }
            }
        }
    }

    private fun navigateToManageTerrarium(name: String) {
        val intent = Intent(this, manageTerrarium::class.java)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    private fun navigateToGoogleMap() {
        val intent = Intent(this, googleMap::class.java)
        startActivity(intent)
    }
}
