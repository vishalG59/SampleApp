package com.app.recipehub.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.app.recipehub.domain.model.Difficulty
import com.app.recipehub.domain.model.NutritionalValue
import com.app.recipehub.domain.model.Recipe
import com.app.recipehub.presentation.theme.RecipeHubDimens
import com.app.ui.component.AppCoilImageView

/**
 * [RecipeCardView] is a composable function that displays a recipe card.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCardView(
    modifier: Modifier = Modifier,
    recipe: Recipe
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = RecipeHubDimens.recipe_card_elevation),
        shape = RoundedCornerShape(RecipeHubDimens.recipe_card_corner_radius),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.Top
        ) {
            AppCoilImageView(
                modifier = Modifier
                    .width(RecipeHubDimens.recipe_card_image_width)
                    .aspectRatio(3f / 4f)
                    .clip(
                        RoundedCornerShape(
                            topStart = RecipeHubDimens.recipe_card_corner_radius,
                            bottomStart = RecipeHubDimens.recipe_card_corner_radius
                        )
                    ),
                imageUrl = recipe.imageUrl,
                contentDescription = "Image of ${recipe.name}",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(RecipeHubDimens.recipe_card_image_to_text_spacing))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        top = RecipeHubDimens.recipe_card_padding_vertical,
                        end = RecipeHubDimens.recipe_card_padding_horizontal
                    )
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (recipe.headline.isNotBlank()) {
                    Spacer(modifier = Modifier.height(RecipeHubDimens.recipe_card_headline_spacing))
                    Text(
                        text = recipe.headline,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(RecipeHubDimens.recipe_card_description_spacing))

                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview
@Composable
fun RecipeCardViewPreview() {
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

    RecipeCardView(
        modifier = Modifier,
        recipe = previewRecipe
    )
}
