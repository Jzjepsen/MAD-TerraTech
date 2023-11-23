package com.example.terratech

import android.graphics.Paint.Align
import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
        fontSize = 20.sp,
        fontWeight = FontWeight.Black,
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
        Layout("Android")
    }
}