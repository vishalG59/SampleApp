package com.app.ui.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/**
 * [AppCoilImageView] A composable function that load & displays an image from network using Coil library.
 */
@Composable
fun AppCoilImageView(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeDescriptionImageViewPreview() {
    AppCoilImageView(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 10f)
            .heightIn(max =300.dp),
        imageUrl = "https://img.hellofresh.com/f_auto,q_auto/hellofresh_s3/image/533143aaff604d567f8b4571.jpg",
        contentDescription = "Image of Crispy Fish Goujons ",
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun RecipeListItemImageViewPreview() {
    AppCoilImageView(
        modifier = Modifier
            .width(120.dp)
            .aspectRatio(3f / 4f)
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp,
                    bottomStart = 12.dp
                )
            ),
        imageUrl = "https://img.hellofresh.com/f_auto,q_auto/hellofresh_s3/image/533143aaff604d567f8b4571.jpg",
        contentDescription = "Image of Crispy Fish Goujons ",
        contentScale = ContentScale.Crop
    )
}
