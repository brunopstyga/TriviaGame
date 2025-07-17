package com.navent.entertainmentcompse.ui.select

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.navent.entertainmentcompse.R
import com.navent.entertainmentcompse.commons.Select
import java.util.Locale

@Composable
fun Difficulty(
    selectedDifficulty: String?,
    onDifficultySelected: (String) -> Unit
) {
    val difficulties = listOf("easy", "medium", "hard")

    Select(
        items = difficulties,
        selectedItem = selectedDifficulty,
        onItemSelected = onDifficultySelected,
        itemToString = { it.replaceFirstChar { item ->
            if (item.isLowerCase()) item.titlecase(Locale.ROOT) else item.toString() } },
        placeholder = stringResource(R.string.select_difficulty)
    )
}