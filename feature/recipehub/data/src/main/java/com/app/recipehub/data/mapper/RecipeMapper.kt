package com.app.recipehub.data.mapper

import com.app.recipehub.data.local.RecipeEntity
import com.app.recipehub.data.remote.model.RecipeDTO
import com.app.recipehub.domain.model.Difficulty
import com.app.recipehub.domain.model.Ingredient
import com.app.recipehub.domain.model.NutritionalValue
import com.app.recipehub.domain.model.Recipe
import timber.log.Timber

/**
 * Maps a [RecipeDTO] from the data layer to a [Recipe] in the domain layer.
 */
fun RecipeDTO.toDomainModel(): Recipe {
    val domainDifficulty = mapDifficulty(this.difficulty)

    val allIngredients = mutableListOf<Ingredient>()
    this.deliverableIngredients?.forEach {
        allIngredients.add(Ingredient(name = it, isDeliverable = true))
    }
    this.undeliverableIngredients?.forEach {
        allIngredients.add(Ingredient(name = it, isDeliverable = false))
    }
    return Recipe(
        id = this.id,
        name = this.name,
        headline = this.headline,
        description = this.description,
        calories = parseNutritionalValue(this.calories, "kcal"),
        carbohydrates = parseNutritionalValue(this.carbos, "g"),
        fats = parseNutritionalValue(this.fats, "g"),
        proteins = parseNutritionalValue(this.proteins, "g"),
        imageUrl = this.image,
        thumbnailUrl = this.thumb,
        difficulty = domainDifficulty,
        countryCode = this.country,
        favoriteCount = this.favorites ?: 0,
        ingredients = allIngredients.distinctBy { it.name },
        applicableWeeks = this.weeks ?: emptyList()
    )
}

/**
 * Maps a list of [RecipeDTO] to a list of [Recipe].
 */
fun List<RecipeDTO>.toDomainModelList(): List<Recipe> {
    return this.map { it.toDomainModel() }
}

/**
 * Helper function to map DTO difficulty Int to domain Difficulty enum.
 */
private fun mapDifficulty(dtoDifficulty: Int): Difficulty {
    return when (dtoDifficulty) {
        0 -> Difficulty.EASY
        1 -> Difficulty.MEDIUM
        2 -> Difficulty.HARD
        else -> {
            Timber.w("Unknown DTO difficulty value: $dtoDifficulty, defaulting to UNKNOWN.")
            Difficulty.UNKNOWN
        }
    }
}

/**
 * Helper function to parse nutritional strings like "516 kcal" or "47 g".
 * @param valueString The string to parse (e.g., "516 kcal").
 * @param defaultUnit The unit to assume if only a number is found.
 */
private fun parseNutritionalValue(
    valueString: String?,
    defaultUnit: String = "g"
): NutritionalValue? {
    if (valueString.isNullOrBlank()) return null

    // Regex to capture a number (integer or float) and an optional unit
    val regex = """(\d+\.?\d*)\s*([a-zA-Zμ]+)?""".toRegex()
    val matchResult = regex.find(valueString)

    return if (matchResult != null) {
        try {
            val quantity = matchResult.groupValues[1].toFloat()
            val unit = matchResult.groupValues[2].takeIf { it.isNotBlank() } ?: defaultUnit
            NutritionalValue(quantity, unit)
        } catch (e: NumberFormatException) {
            Timber.e(e, "Failed to parse nutritional value quantity from: $valueString")
            null
        }
    } else {
        Timber.w("Could not parse nutritional value string: $valueString")
        null
    }
}

fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        name = name,
        headline = headline,
        description = description,
         calories = calories,
         carbohydrates = carbohydrates,
         fats = fats,
         proteins = proteins,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        countryCode = countryCode,
        favoriteCount = favoriteCount,
         difficulty = difficulty,
         ingredients = ingredients,
         applicableWeeks = applicableWeeks
    )
}

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        id = id,
        name = name,
        headline = headline,
        description = description,
        calories = null,
        carbohydrates = null,
        fats = null,
        proteins = null,
        imageUrl = imageUrl,
        thumbnailUrl = thumbnailUrl,
        countryCode = countryCode,
        favoriteCount = favoriteCount,
        difficulty = Difficulty.EASY,
        ingredients = listOf(Ingredient("test", true)),
        applicableWeeks = listOf("", "")
    )
}


