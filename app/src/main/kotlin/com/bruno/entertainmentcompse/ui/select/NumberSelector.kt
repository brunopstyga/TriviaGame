package com.bruno.entertainmentcompse.ui.select

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bruno.entertainmentcompse.R
import com.bruno.entertainmentcompse.commons.Select

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
        placeholder = stringResource(R.string.select_amount)
    )
}
