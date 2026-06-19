package com.deviar.petask

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deviar.petask.common.ui.theme.Background
import com.deviar.petask.common.ui.theme.PetaskTheme
import com.deviar.petask.common.ui.navigation.NavHost
import com.deviar.petask.common.ui.navigation.PetaskNavigationBar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetaskTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Background
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        mainViewModel.isLogged
                    )
                    Log.i(
                        "Iara",
                        Firebase.auth.currentUser?.email ?: "NO USER"
                    )
                }
            }
        }
    }
}

