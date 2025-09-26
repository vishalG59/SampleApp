package com.app.recipehub.data.local

import androidx.room.*
import com.app.recipehub.domain.model.Difficulty
import com.app.recipehub.domain.model.Ingredient
import com.app.recipehub.domain.model.NutritionalValue

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val headline: String,
    val description: String,

    @TypeConverters(Converters::class)
    @Embedded(prefix = "calories_")
    val calories: NutritionalValue?,

    @TypeConverters(Converters::class)
    @Embedded(prefix = "carbs_")
    val carbohydrates: NutritionalValue?,

    @TypeConverters(Converters::class)
    @Embedded(prefix = "fats_")
    val fats: NutritionalValue?,

    @TypeConverters(Converters::class)
    @Embedded(prefix = "proteins_")
    val proteins: NutritionalValue?,

    val imageUrl: String,
    val thumbnailUrl: String,
    @TypeConverters(Converters::class)
    val difficulty: Difficulty,
    val countryCode: String?,
    val favoriteCount: Int,

    @TypeConverters(Converters::class)
    val ingredients: List<Ingredient>,

    @TypeConverters(Converters::class)
    val applicableWeeks: List<String>
)
