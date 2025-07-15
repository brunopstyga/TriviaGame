package com.navent.entertainmentcompse.ui.select

import androidx.compose.runtime.Composable
import com.navent.entertainmentcompse.commons.Select
import java.util.Locale

@Composable
fun Type(
    selectedDifficulty: String?,
    onDifficultySelected: (String) -> Unit
) {

    Select(
        items = listOf("Any Type", "Multiple Choice", "True/False"),
        selectedItem = selectedDifficulty,
        onItemSelected = onDifficultySelected,
        itemToString = { it.replaceFirstChar { item ->
            if (item.isLowerCase()) item.titlecase(Locale.ROOT) else item.toString() } },
        // Convertir a mayúscula la primera letra
        placeholder = "Selecciona un tipo"
    )
}