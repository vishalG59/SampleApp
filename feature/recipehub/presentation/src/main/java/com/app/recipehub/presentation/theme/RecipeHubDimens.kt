package com.app.recipehub.presentation.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.ui.theme.CoreDimens
import com.app.ui.theme.CoreDimens.corner_radius_medium
import com.app.ui.theme.CoreDimens.elevation_level2
import com.app.ui.theme.CoreDimens.space_1x
import com.app.ui.theme.CoreDimens.space_2x
import com.app.ui.theme.CoreDimens.space_3x

/**
 * [RecipeHubDimens] holds dimension values specific to the RecipeHub feature.
 */
object RecipeHubDimens {
    // Recipe Card Specific Dimensions.
    val recipe_card_elevation: Dp = elevation_level2
    val recipe_card_corner_radius: Dp =
        corner_radius_medium

    val recipe_card_image_width: Dp =
        120.dp
    val recipe_card_padding_horizontal: Dp =
        space_3x
    val recipe_card_padding_vertical: Dp =
        space_3x

    val recipe_card_image_to_text_spacing: Dp =
        space_3x
    val recipe_card_headline_spacing: Dp = space_1x
    val recipe_card_description_spacing: Dp = space_2x
    val screen_content_padding: Dp = CoreDimens.padding_screen_content

    // Recipe Details View Specific Dimensions
    val details_hero_image_max_height: Dp = 300.dp
    val details_title_to_headline_spacing: Dp = CoreDimens.space_1_5x
    val details_bottom_spacer_height: Dp = CoreDimens.space_6x

    // Detail Section Specifics
    val details_section_vertical_padding: Dp = CoreDimens.space_3x
    val details_section_divider_bottom_padding: Dp = CoreDimens.space_3x
    val details_section_icon_size: Dp = CoreDimens.icon_size_small
    val details_section_icon_to_title_spacing: Dp = CoreDimens.space_2x
    val details_section_title_to_content_spacing: Dp = CoreDimens.space_2_5x

    // Nutritional Info Grid Specifics Dimensions
    val details_nutritional_info_row_spacing: Dp = CoreDimens.space_1_5x
    val details_section_divider_thickness: Dp = CoreDimens.divider_thickness_thin // 0.5dp
}