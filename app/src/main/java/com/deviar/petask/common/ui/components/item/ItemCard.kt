package com.deviar.petask.common.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deviar.petask.common.ui.theme.Primary
import com.deviar.petask.common.ui.theme.Secondary
import com.deviar.petask.tasks.domain.DateState

@Composable
fun ItemCard(
    dateState: DateState,
    onClickItem: (DateState) -> Unit,
) {
    Card(
        modifier = Modifier
            .width(60.dp)
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Primary)
                .clickable(
                    onClick = {
                        onClickItem(dateState)
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = dateState.showDate,
                color = Secondary,
            )
        }
    }
}

