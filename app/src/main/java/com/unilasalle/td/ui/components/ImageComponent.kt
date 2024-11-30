package com.unilasalle.td.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.unilasalle.td.services.network.ApiService
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

/**
 * Image component
 *
 * This component displays a random image
 */
@Composable
fun ImageComponent() {
    val apiService = ApiService.createImageService()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var imageUrl by remember { mutableStateOf<String?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }

    fun fetchImage() {
        coroutineScope.launch {
            val response = apiService.getRandomImage()
            imageUrl = response.message
        }
    }

    LaunchedEffect(Unit) {
        fetchImage()
    }

    // SwipeRefresh is a composable that provides pull-to-refresh functionality
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { fetchImage() }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, _ ->
                        fetchImage()
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imageUrl?.let {
                val painter: Painter = rememberImagePainter(
                    request = ImageRequest.Builder(context)
                        .data(it)
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = "Random Dog Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                CircularProgressIndicator()
            }
        }
    }
}