package com.deviar.petask.auth.data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth

    fun login(
        email: String,
        password: String,
        onResult: (Boolean) -> Unit
    ){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }
}
