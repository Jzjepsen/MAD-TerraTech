package com.example.terratech

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.terratech.firestore.service.Firestore
import com.example.terratech.ui.theme.Purple40
import com.example.terratech.ui.theme.TerraTechTheme
import com.google.android.gms.base.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = Firebase.auth
        FirebaseApp.initializeApp(this);
        val db = FirebaseFirestore.getInstance()
        val service = Firestore(db, auth)
        auth.currentUser
        setContent {
            TerraTechTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignIn(Modifier,service)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignIn(modifier: Modifier = Modifier, service: Firestore){
    val username =  remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    // Use tab to change focus
    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Account image",
            modifier = modifier
                .size(100.dp)
                .fillMaxWidth()
                .padding(16.dp,4.dp)
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        OutlinedTextField(
            modifier = modifier
                .padding(16.dp, 4.dp)
                .focusRequester(focusRequester1)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.key == Key.Tab && keyEvent.type == KeyEventType.KeyUp) {
                        focusRequester2.requestFocus()
                        true
                    } else false},
            value = username.value,
            onValueChange = { newText -> username.value = newText},
            label = { Text("Username") }
        )

            OutlinedTextField(
                modifier = modifier
                    .padding(16.dp, 4.dp)
                    .focusRequester(focusRequester2)
                .onKeyEvent { keyEvent ->
                if (keyEvent.key == Key.Tab && keyEvent.type == KeyEventType.KeyUp) {
                    focusRequester1.requestFocus()
                    true
                } else false},
            value = password.value,
            onValueChange = { newText -> password.value = newText },
            label = { Text("Password") } ,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        val context = LocalContext.current
        Button(
            onClick = {
                scope.launch {
                    val user = service.login(username.value, password.value)
                    if (user != null){
                    val intent = Intent(context, overviewOfTerrarium::class.java)
                    context.startActivity(intent)
            } else {
                // Show wrong login
            }}},
            modifier = modifier
                .padding(16.dp, 0.dp)
        ) {
            Text(
                text = "Sign in",
                fontSize = 16.sp,
                modifier = modifier.padding(0.dp, 6.dp)
            )
        }

        TextButton(onClick = { }
        ) {
            Text(text = "Sign Up", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview6() {
    TerraTechTheme {
        //SignIn({ })
    }
}