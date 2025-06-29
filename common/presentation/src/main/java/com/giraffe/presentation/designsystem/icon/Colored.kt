package com.giraffe.presentation.designsystem.icon

import com.giraffe.presentation.R

data class Colored(
    val logo: Int,
    val confusedFace: Int,
    val facebook: Int,
    val frowningFace: Int,
    val instagram: Int,
    val iraqFlag: Int,
    val neutralFace: Int,
    val smilingFaceWithSmilingEyes: Int,
    val starStruck: Int,
    val tiktok: Int,
    val ukFlag: Int,
    val x: Int,
    val youtube: Int,
)

val coloredIcons = Colored(
    logo = R.drawable.colored_cineverse_logo,
    confusedFace = R.drawable.colored_confused_face,
    facebook = R.drawable.colored_facebook,
    frowningFace = R.drawable.colored_frowning_face,
    instagram = R.drawable.colored_instagram,
    iraqFlag = R.drawable.colored_iraq_flag,
    neutralFace = R.drawable.colored_neutral_face,
    smilingFaceWithSmilingEyes = R.drawable.colored_smiling_face_with_smiling_eyes,
    starStruck = R.drawable.colored_star_struck,
    tiktok = R.drawable.colored_tiktok,
    ukFlag = R.drawable.colored_uk_flag,
    x = R.drawable.colored_x,
    youtube = R.drawable.colored_youtube
)