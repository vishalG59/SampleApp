package com.app.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview

/**
 * [AppCircularProgressIndicator] Displays a circular progress indicator based on the loading state.
 *
 * @param isLoading If `true`, the circular progress indicator is shown; otherwise, nothing is displayed.
 */
@Composable
fun AppCircularProgressIndicator(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize().testTag("progressIndicator"),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeListPreview() {
    AppCircularProgressIndicator(isLoading = true)
}
