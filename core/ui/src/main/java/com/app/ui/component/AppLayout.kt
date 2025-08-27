package com.app.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * [AppLayout] composable function to create a layout with a top app bar and content.
 *
 * @param title The title to be displayed in the top app bar.
 * @param showBackButton Boolean(true/false) Whether to show a back button in the top app bar.
 * @param onBackClicked Optional lambda to be invoked when the back button is clicked.
 * @param actions Optional content for actions in the top app bar.
 * @param content The main content of the layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLayout(
    title: String,
    showBackButton: Boolean = false,
    onBackClicked: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text(title) },
                navigationIcon = {
                    if (showBackButton && onBackClicked != null) {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = actions,
                colors = TopAppBarDefaults.topAppBarColors( // Optional: Default colors
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
