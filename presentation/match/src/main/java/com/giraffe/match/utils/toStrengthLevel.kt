package com.giraffe.match.utils

import com.giraffe.imageviewer.model.StrengthLevel
import com.giraffe.user.entity.ContentPreference

fun ContentPreference.toStrengthLevel(): StrengthLevel {
    return when (this) {
        ContentPreference.HIDE_EXPLICIT -> StrengthLevel.HIDE_EXPLICIT
        ContentPreference.STRICT -> StrengthLevel.STRICT
        ContentPreference.SHOW_ALL -> StrengthLevel.SHOW_ALL
    }
}
