package com.deviar.petask.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth

    fun login(
        email: String,
        password: String,
        onResult: (String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(null)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        onResult("Invalid email or password")

                    } else {

                        onResult(task.exception?.message)
                    }
                }
            }
    }

    fun register(
        email: String,
        password: String,
        onResult: (String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(null)
                } else {
                    onResult(task.exception?.message)
                }
            }
    }

    fun resetPassword(
        email: String,
        onResult: (Boolean) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->

                onResult(task.isSuccessful)
            }
    }

    fun signOut() {
        auth.signOut()
    }
}
