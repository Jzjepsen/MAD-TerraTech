package com.example.terratech

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.terratech.firestore.service.Firestore
import com.example.terratech.ui.theme.TerraTechTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class signUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val auth = Firebase.auth
            FirebaseApp.initializeApp(this);
            val db = FirebaseFirestore.getInstance()
            val service = Firestore(db, auth)
            auth.currentUser
            TerraTechTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUp(Modifier,service)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SignUp(modifier: Modifier = Modifier, service: Firestore){
    val username =  remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    //Error message
    val loginErrorMessage = remember { mutableStateOf<String?>(null) }
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
                .padding(16.dp, 4.dp),
            value = username.value,
            onValueChange = {
                    newText -> username.value = newText
                if (loginErrorMessage.value != null) {
                    loginErrorMessage.value = null // Reset error message when user starts typing
                } },
            label = { Text("Username") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }

        )

        OutlinedTextField(
            modifier = modifier
                .padding(16.dp, 4.dp),
            value = password.value,
            onValueChange = { newText -> password.value = newText
                if (loginErrorMessage.value != null) {
                    loginErrorMessage.value = null // Reset error message when user starts typing
                }},
            label = { Text("Password") } ,
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "password") }
        )

        OutlinedTextField(
            modifier = modifier
                .padding(16.dp, 4.dp),
            value = confirmPassword.value,
            onValueChange = { newText -> confirmPassword.value = newText
                if (loginErrorMessage.value != null) {
                    loginErrorMessage.value = null // Reset error message when user starts typing
                }},
            label = { Text("Confirm password") } ,
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "password") }
        )

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp))

        val context = LocalContext.current
        Button(
            onClick = {
                scope.launch {
                    if (isValidEmail(username.value)) {
                        if (password.value.length > 5) {
                        if (password.value == confirmPassword.value ) {
                            val user = service.signup(username.value, password.value)
                            val intent = Intent(context, overviewOfTerrarium::class.java)
                            context.startActivity(intent)
                        }
                        else {
                            loginErrorMessage.value = "The passwords is not the same"
                        }
                    }
                    else{
                            loginErrorMessage.value = "The password must contain at least 6 characters."
                        }
                    }
                    else {
                        loginErrorMessage.value = "Invalid email format"
                    }
                }},
            modifier = modifier
                .padding(16.dp, 0.dp)
        ) {
            Text(
                text = "Sign up",
                fontSize = 16.sp,
                modifier = modifier.padding(0.dp, 6.dp)
            )
        }

        if (loginErrorMessage.value != null) {
            Text(loginErrorMessage.value!!, color = Color.Red)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview7() {
    TerraTechTheme {

    }
}