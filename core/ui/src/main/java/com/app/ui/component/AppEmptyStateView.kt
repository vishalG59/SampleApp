package com.app.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.app.ui.theme.CoreDimens


/**
 * [AppEmptyStateView] composable function to display a full-page empty state message.
 *
 * @param modifier The modifier to be applied to the Column container.
 * @param title The main title message to display.
 * @param message An optional subtitle or more detailed message.
 * @param icon An optional icon to display above the title.
 * @param actionText An optional text for a call-to-action button.
 * @param onActionClick An optional lambda to be invoked when the action button is clicked.
 */
@Composable
fun AppEmptyStateView(
    modifier: Modifier = Modifier,
    title: String,
    message: String? = null,
    icon: ImageVector? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize() // Fills the available space
            .padding(CoreDimens.padding_screen_default), // Consistent padding
        verticalArrangement = Arrangement.Center, // Centers content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null, // Content description can be part of the title
                modifier = Modifier.size(CoreDimens.icon_size_large), // Example large icon size
                tint = MaterialTheme.colorScheme.primary // Or onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(CoreDimens.space_3x)) // Space between icon and title
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        if (message != null) {
            Spacer(modifier = Modifier.height(CoreDimens.space_2x)) // Space between title and message
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        if (actionText != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(CoreDimens.space_4x)) // Space before action button
            Button(
                onClick = onActionClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(text = actionText)
            }
        }
    }
}

// Preview for the EmptyStateView
@Preview(showBackground = true, name = "Empty State with Icon and Action")
@Composable
fun EmptyStateViewPreviewWithAction() {
    MaterialTheme { // Wrap with your app's theme for accurate preview
        AppEmptyStateView(
            title = "No Recipes Found!",
            message = "Looks like no recipes available at the moment Try after some time.",
            icon = Icons.Outlined.Close, // Example icon
            actionText = "Close",
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty State - Title Only")
@Composable
fun EmptyStateViewPreviewTitleOnly() {
    MaterialTheme {
        AppEmptyStateView(
            title = "Nothing to see here."
        )
    }
}
