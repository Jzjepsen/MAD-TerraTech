package com.example.terratech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.terratech.ui.theme.TerraTechTheme

class manageTerrarium : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {

                    val intentHome = Intent(this@manageTerrarium, MainActivity::class.java)
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



                    ManageTerrariumScreen("1")

                }
                }
            }
        }
    }
}

@Composable
fun ManageTerrariumScreen(number: String, modifier: Modifier = Modifier) {

    TerraTechTheme {

            Box (
                contentAlignment = Alignment.TopCenter
            ) {
                TitleText("1")
            }

            Box(
                contentAlignment = Alignment.Center

            ) {
                Column {


                    SwitchTemplate("Heater")
                    SwitchTemplate("Flow Nozzle")
                    SwitchTemplate("Light")

                    ThresholdTemplate("Temperature Threshold")
                    ThresholdTemplate("Humidity Threshold")
                }
            }

    }




}

@Composable
fun TitleText(TerrariumNumber: String){
    Text(
        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
        text = "Manage Terrarium $TerrariumNumber",
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center
    )

}

@Composable
fun SwitchTemplate(label: String) {

    TerraTechTheme {
        Box (
            modifier = Modifier
                .padding(horizontal = 60.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)


            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(text = label,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp))
                    Spacer(modifier = Modifier.width(width = 20.dp))
                    CustomSwitch()
                }
            }

        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
    }


}

@Composable
fun ThresholdTemplate(label: String){

        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
            )

            Row {
                NumberInputTemplate(label = "Min.")
                Spacer(modifier = Modifier.width(width = 5.dp))
                NumberInputTemplate(label = "Max.")
            }
        }

}

@Composable
fun NumberInputTemplate (label: String){
    Box (modifier = Modifier.width(100.dp)) {
        StylisedTextField(placeholderText = label)
    }


}

@Composable
fun CustomSwitch() {
    var checked by remember { mutableStateOf(true) }
    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        }

    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StylisedTextField(placeholderText: String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(placeholderText) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 20.sp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
    )
}

 

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    TerraTechTheme {
        ManageTerrariumScreen("Android")
    }
}