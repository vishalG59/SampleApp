package com.app.recipehub.domain.model

/**
 * Represents a recipe in the domain layer.
 * This model is independent from data source specifics DTO/Response.
 */

data class Recipe(
    val id: String,
    val name: String,
    val headline: String,
    val description: String,
    val calories: NutritionalValue?,
    val carbohydrates: NutritionalValue?,
    val fats: NutritionalValue?,
    val proteins: NutritionalValue?,
    val imageUrl: String,
    val thumbnailUrl: String,
    val difficulty: Difficulty,
    val countryCode: String?,
    val favoriteCount: Int,
    val ingredients: List<Ingredient>,
    val applicableWeeks: List<String>
)

data class NutritionalValue(
    val quantity: Float,
    val unit: String
)

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD,
    UNKNOWN
}
data class Ingredient(
    val name: String,
    val isDeliverable: Boolean
)

