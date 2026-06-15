package com.deviar.petask

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainViewModel: ViewModel() {

    val isLogged = Firebase.auth.currentUser != null
}