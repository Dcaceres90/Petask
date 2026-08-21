package com.deviar.petask.goals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deviar.petask.common.ui.components.button.FloatingButton

@Composable
fun GoalsScreen(
    modifier: Modifier,
    goalsViewModel: GoalsViewModel
) {
    val uiState by goalsViewModel.iuState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingButton(
                modifier = Modifier.padding(
                    end = 20.dp,
                    bottom = 70.dp,
                ),
                onClickFloating = {
                    goalsViewModel.createTestGoal()
                }
            )
        },
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {


            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Text("Cantidad de objetivos: ${uiState.goals.size}")
            }

        }

    }






}


