package com.example.cukcuk.presentation.ui.inventory.inventory_form

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cukcuk.R
import com.example.cukcuk.utils.ImageHelper.loadAllImagesFromAssets

@Composable
fun ImageInventoryForm(
    onSelectImage: (String) -> Unit,
    onCloseForm: () -> Unit,
) {

    val context = LocalContext.current
    val imageListState = remember { mutableStateOf<List<Pair<String, ImageBitmap>>>(emptyList()) }
    val isLoaded = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val images = loadAllImagesFromAssets(context, "icon_default")
        imageListState.value = images
        isLoaded.value = true
    }

    if (!isLoaded.value) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(enabled = false) {}
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .background(
                    color = colorResource(R.color.background_form_select_image_inventory),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(vertical = 10.dp)
        ) {
            ImageInventoryGrid(
                imageList = imageListState.value,
                onSelectImage = {
                    onSelectImage(it)
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = "Hủy bỏ",
                    textAlign = TextAlign.Right,
                    color = colorResource(R.color.main_color),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onCloseForm()
                        }
                )
            }
        }
    }
}

@Composable
fun ImageInventoryGrid(
    imageList: List<Pair<String, ImageBitmap>>,
    onSelectImage: (String) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier
            .height(640.dp)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(imageList.size) { index ->
            val (fileName, imageBitmap) = imageList[index]

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelectImage(fileName)
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun ImageInventoryFormPreview() {
    ImageInventoryForm(
        onSelectImage = {},
        onCloseForm = {}
    )
}