package com.example.presentation.enums

import androidx.annotation.StringRes
import com.example.presentation.R

enum class Language(
    @StringRes val title: Int,
    val code: String,
) {
    English(
        title = R.string.language_en,
        code = "en"
    ),

    Vietnamese(
        title = R.string.language_vi,
        code = "vi"
    ),

    Chinese(
        title = R.string.language_zh,
        code = "zh"
    ),
    Japanese(
        title = R.string.language_ja,
        code = "ja"
    ),
    Korean(
        title = R.string.language_ko,
        code = "ko"
    ),
    Spanish(
        title = R.string.language_es,
        code = "es"
    ),
    French(
        title = R.string.language_fr,
        code = "fr"
    ),
    Italian(
        title = R.string.language_it,
        code = "it"
    ),
}