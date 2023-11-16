package com.example.terratech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.terratech.ui.theme.TerraTechTheme


// TODO: set it so it has "manage <terrarium name>" displayed at the top



class manageTerrarium : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerraTechTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Layout("1")
                }
            }
        }
    }
}

@Composable
fun Layout(number: String, modifier: Modifier = Modifier) {
    Column {


        Text(
            text = "Manage Terrarium $number",
            modifier = modifier
        )
        SwitchTemplate("Heater")
        SwitchTemplate("Flow Nozzle")
        SwitchTemplate("Light")
        ThresholdTemplate("Temperature Threshold")
        ThresholdTemplate("Humidity Threshold")
    }



}

@Composable
fun SwitchTemplate(label: String) {

    TerraTechTheme {
        Surface (
            color = MaterialTheme.colorScheme.secondaryContainer,

        ){
            Row (
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(text = label)
                Spacer(modifier = Modifier.width(width = 20.dp))
                CustomSwitch()
            }
        }
    }

}

@Composable
fun ThresholdTemplate(label: String){
    Column(
        modifier = Modifier.padding(vertical = 8.dp)

    ) {
        Text(text = label)


        Row {
            TextField("Min")
            Spacer(modifier = Modifier.width(width = 5.dp))
            TextField("Max")
        }
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


//TODO: restrict to numbers, restrict the use of enter
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(placeholderText: String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text(placeholderText) },
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    TerraTechTheme {
        Layout("Android")
    }
}