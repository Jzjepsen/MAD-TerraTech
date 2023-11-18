package com.example.terratech.firestore.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Firestore(private val api: FirebaseFirestore, private val auth: FirebaseAuth) {
    companion object {
        const val TAG = "FIRE_STORE_SERVICE"
    }
    suspend fun login(email: String, password: String) {
        suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser ?: throw Exception("Something wrong")
                        val signedInUser = user.email?.let { User(user.providerId, it) }
                            ?: throw Exception("createUserWithEmail:$email failure")
                        continuation.resume(signedInUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        continuation.resume(task.exception ?: Exception("Login failed"))
                    }
                }
        }
    }

    suspend fun signup(email: String, password: String) {
        suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser ?: throw Exception("Something wrong")
                        val signedInUser = user.email?.let { User(user.providerId, it) }
                            ?: throw Exception("createUserWithEmail:$email failure")
                        continuation.resume(signedInUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        throw throw Exception("createUserWithEmail: $email failure", task.exception)
                    }
                }
        }
    }
}
