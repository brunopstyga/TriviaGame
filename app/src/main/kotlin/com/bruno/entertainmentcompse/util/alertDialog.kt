package com.bruno.entertainmentcompse.util

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.bruno.entertainmentcompse.navigation.Screen
import com.bruno.entertainmentcompse.navigation.toRoute

@Composable
fun AlertGameDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    confirmText: String = "OK",
    onConfirm: () -> Unit = onDismiss
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText)
            }
        }
    )
}

fun Screen.toTriviaRoute(
    categoryId: Int,
    difficulty: String,
    type: String
): String {
    return "${this.toRoute()}/$categoryId/$difficulty/$type"
}

