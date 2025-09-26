package com.app.recipehub.data.local

import androidx.room.TypeConverter
import com.app.recipehub.domain.model.Difficulty
import com.app.recipehub.domain.model.Ingredient
import com.app.recipehub.domain.model.NutritionalValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromDifficulty(value: Difficulty): String = value.name

    @TypeConverter
    fun toDifficulty(value: String): Difficulty =
        runCatching { Difficulty.valueOf(value) }.getOrDefault(Difficulty.UNKNOWN)

    @TypeConverter
    fun fromIngredientList(list: List<Ingredient>): String = gson.toJson(list)

    @TypeConverter
    fun toIngredientList(json: String): List<Ingredient> {
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromNutritionalValue(value: NutritionalValue?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toNutritionalValue(value: String?): NutritionalValue? {
        if (value.isNullOrEmpty()) return null
        val type = object : TypeToken<NutritionalValue>() {}.type
        return gson.fromJson(value, type)
    }
}
