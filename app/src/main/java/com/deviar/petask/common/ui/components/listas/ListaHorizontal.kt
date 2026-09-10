package com.deviar.petask.common.ui.components.listas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deviar.petask.common.ui.components.item.ItemCard
import com.deviar.petask.tasks.domain.DateState

@Composable
fun ListHorizontalCustom(
    items: List<DateState>,
    onClickItem: (DateState) -> Unit,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            ItemCard(
                dateState = item,
                onClickItem = onClickItem,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListHorizontalCustomPreview() {
    ListHorizontalCustom(
        items = listOf(),
        onClickItem = {},
    )
}