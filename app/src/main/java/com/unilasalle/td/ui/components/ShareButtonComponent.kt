package com.unilasalle.td.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Share button component
 *
 * This component displays a share button
 *
 * @param context The context
 * @param text The text to share
 */
@Composable
fun ShareButtonComponent(context: Context, text: String) {
    Icon(
        imageVector = Icons.Default.Share,
        contentDescription = "Share",
        tint = Color.Black,
        modifier = Modifier.clickable {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    )
}