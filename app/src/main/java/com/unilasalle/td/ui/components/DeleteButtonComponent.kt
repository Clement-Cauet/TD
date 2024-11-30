package com.unilasalle.td.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Delete button component
 *
 * This component displays a delete button
 *
 * @param onDelete The function to call when the button is clicked
 */
@Composable
fun DeleteButtonComponent(onDelete:() -> Unit) {
    Icon(
        imageVector = Icons.Default.Delete,
        contentDescription = "Delete",
        tint = Color.Black,
        modifier = Modifier.clickable { onDelete() }
    )
}