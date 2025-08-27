package com.app.recipehub.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * [RecipeDTO] is response model for recipe API fetched from server.
 *
 */
data class RecipeDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("headline")
    val headline: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("calories")
    val calories: String,

    @SerializedName("carbos")
    val carbos: String,

    @SerializedName("fats")
    val fats: String,

    @SerializedName("proteins")
    val proteins: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("thumb")
    val thumb: String,

    @SerializedName("difficulty")
    val difficulty: Int,

    @SerializedName("time")
    val time: String,

    @SerializedName("country")
    val country: String? = null,

    @SerializedName("favorites")
    val favorites: Int? = null,

    @SerializedName("deliverable_ingredients")
    val deliverableIngredients: List<String>? = null,

    @SerializedName("undeliverable_ingredients")
    val undeliverableIngredients: List<String>? = null,

    @SerializedName("weeks")
    val weeks: List<String>? = null
)
