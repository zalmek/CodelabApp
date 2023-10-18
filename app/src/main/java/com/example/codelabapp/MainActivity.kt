package com.example.codelabapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.codelabapp.model.Photo
import com.example.codelabapp.ui.theme.CodelabAppTheme
import com.example.codelabapp.vm.PhotoViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var selected by remember {
                mutableStateOf(-1)
            }
            val scope = rememberCoroutineScope()
            val viewModel: PhotoViewModel = viewModel()
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.getAllPhotos()
                }
            }

            val photos by viewModel.photoUiState.collectAsState()
            CodelabAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (selected != -1) {
                        photos.find { it.id == selected }
                            ?.let { PhotoCard(photo = it, backHandle = { selected = -1 }) }
                    } else {
                        LazyColumn {
                            items(photos) {
                                PhotoCard(photo = it, Modifier.clickable { selected = it.id })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodelabAppTheme {
        Greeting("Android")
    }
}

@Composable
fun PhotoCard(
    photo: Photo,
    modifier: Modifier = Modifier,
    backHandle: () -> Unit = {},
) {
    BackHandler {
        backHandle()
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(
                elevation = 40.dp,
                ambientColor = Color(0x14000000)
            )
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 16.dp))
    ) {
        AsyncImage(
            model = photo.url,
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(top = 12.dp)
                .width(328.dp)
                .height(301.dp)
                .clip(RoundedCornerShape(30.dp))
        )
        Text(
            text = photo.title,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 15.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                letterSpacing = 0.2.sp,
            ),
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 16.dp)
        )
    }
}