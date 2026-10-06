package com.deviar.petask.goals.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.deviar.petask.common.ui.theme.GoldCoin
import com.deviar.petask.common.ui.theme.Primary
import com.deviar.petask.goals.ui.state.GoalUiState

@Composable
fun GoalItem(
    goal : GoalUiState,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable{ onClick()}
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = goal.isComplete,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary
                )
            )

            Text(
                text = goal.text,
                textDecoration = if (goal.isComplete) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "+${goal.goalType.coinValue}",
                color = GoldCoin,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Gray
                )
            }
        }
    }

}