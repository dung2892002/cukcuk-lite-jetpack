package com.example.presentation.enums

import androidx.annotation.StringRes
import com.example.presentation.R

enum class CalculatorButton(
    @StringRes val label: Int,
) {
    ZERO(R.string.key_0),
    ONE(R.string.key_1),
    TWO(R.string.key_2),
    THREE(R.string.key_3),
    FOUR(R.string.key_4),
    FIVE(R.string.key_5),
    SIX(R.string.key_6),
    SEVEN(R.string.key_7),
    EIGHT(R.string.key_8),
    NINE(R.string.key_9),
    TRIPLE_ZERO(R.string.key_000),
    ADD(R.string.key_add),
    SUBTRACT(R.string.key_subtract),
    TOGGLE_SIGN(R.string.key_toggle_sign),
    DECIMAL(R.string.key_decimal),
    CLEAR(R.string.key_clear),
    DECREASE(R.string.key_decrease),
    INCREASE(R.string.key_increase),
    DELETE(R.string.key_delete),
    EQUAL(R.string.key_equal),
    DONE( R.string.key_done),
}