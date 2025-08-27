package com.app.recipehub.presentation.ui.component
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.app.recipehub.domain.model.Difficulty
import com.app.recipehub.domain.model.NutritionalValue
import com.app.recipehub.domain.model.Recipe
import com.app.recipehub.presentation.theme.RecipeHubDimens
import com.app.ui.component.AppCoilImageView
import com.app.ui.component.AppLayout


/**
 * [RecipeDetailsView] is a composable function that displays the details of a recipe.
 */
@Composable
fun RecipeDetailsView(
    modifier: Modifier = Modifier,
    recipe: Recipe
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppCoilImageView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 10f)
                .heightIn(max = RecipeHubDimens.details_hero_image_max_height),
            imageUrl = recipe.imageUrl,
            contentDescription = "Image of ${recipe.name}",
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(all = RecipeHubDimens.screen_content_padding) // Using feature-specific alias for screen padding
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(RecipeHubDimens.details_title_to_headline_spacing))

            if (recipe.headline.isNotBlank()) {
                Text(
                    text = recipe.headline,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Normal
                )
            }

            DetailSection(
                title = "Description",
            ) {
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 1.5
                )
            }

            if (hasNutritionalInfo(recipe)) {
                DetailSection(
                    title = "Nutrition Highlights"
                ) {
                    NutritionalInfoGrid(recipe)
                }
            }
            Spacer(modifier = Modifier.height(RecipeHubDimens.details_bottom_spacer_height))
        }
    }
}

@Composable
private fun DetailSection(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.padding(vertical = RecipeHubDimens.details_section_vertical_padding)) {
        Divider(
            modifier = Modifier.padding(bottom = RecipeHubDimens.details_section_divider_bottom_padding),
            thickness = RecipeHubDimens.details_section_divider_thickness // Using the thin divider
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "$title section icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(RecipeHubDimens.details_section_icon_size)
                )
                Spacer(modifier = Modifier.width(RecipeHubDimens.details_section_icon_to_title_spacing))
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(RecipeHubDimens.details_section_title_to_content_spacing))
        Column(content = content)
    }
}

private fun hasNutritionalInfo(recipe: Recipe): Boolean { // Unchanged
    return recipe.calories != null || recipe.carbohydrates != null || recipe.fats != null || recipe.proteins != null
}

@Composable
private fun NutritionalInfoGrid(recipe: Recipe) {
    Column(verticalArrangement = Arrangement.spacedBy(RecipeHubDimens.details_nutritional_info_row_spacing)) {
        NutritionalInfoRow("Calories", recipe.calories)
        NutritionalInfoRow("Carbs", recipe.carbohydrates)
        NutritionalInfoRow("Fat", recipe.fats)
        NutritionalInfoRow("Protein", recipe.proteins)
    }
}

@Composable
fun NutritionalInfoRow(
    label: String,
    value: NutritionalValue?,
    modifier: Modifier = Modifier
) {
    if (value != null) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${value.quantity} ${value.unit}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}



@Preview
@Composable
fun RecipeDetailContentPreview() {
    MaterialTheme { // Wrap with your theme
        val previewRecipe = Recipe(
            id = "1",
            name = "Delicious Pasta Carbonara",
            headline = "A classic Italian pasta dish made with eggs, cheese, pancetta, and pepper.",
            description = "This creamy pasta carbonara is a quick and easy weeknight meal that's full of flavor. The key to a perfect carbonara is to work quickly and use high-quality ingredients. Don't be intimidated by the raw egg; the heat from the pasta cooks it to create a velvety sauce. Ensure your pancetta is crispy and the Pecorino Romano cheese is freshly grated for the best results. This recipe is a family favorite and is sure to impress your guests with its authentic taste and texture.",
            imageUrl = "https://img.hellofresh.com/f_auto,q_auto/hellofresh_s3/image/533143aaff604d567f8b4571.jpg",
            thumbnailUrl = "",
            calories = NutritionalValue(550f, "kcal"),
            carbohydrates = NutritionalValue(60f, "g"),
            fats = NutritionalValue(25f, "g"),
            proteins = NutritionalValue(20f, "g"),
            difficulty = Difficulty.MEDIUM,
            countryCode = "IT",
            favoriteCount = 120,
            ingredients = emptyList(),
            applicableWeeks = emptyList()
        )
        AppLayout(
            title = previewRecipe.name,
            showBackButton = true,
            onBackClicked = {}
        ) { paddingValues ->
            RecipeDetailsView(
                modifier = Modifier.padding(paddingValues),
                recipe = previewRecipe
            )
        }
    }
}

