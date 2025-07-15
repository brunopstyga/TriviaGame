package com.navent.entertainmentcompse.ui.select

import androidx.compose.runtime.Composable
import com.navent.entertainmentcompse.commons.Select

@Composable
fun NumberSelector(
    selectedNumber: Int?,
    onNumberSelected: (Int) -> Unit
) {
    val numbers = (1..10).toList()

    Select(
        items = numbers,
        selectedItem = selectedNumber,
        onItemSelected = { onNumberSelected(it) },
        itemToString = { it.toString() },
        placeholder = "Selecciona un número"
    )
}
