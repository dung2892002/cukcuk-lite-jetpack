package com.example.presentation.enums

import androidx.annotation.StringRes
import com.example.presentation.R

enum class Screen(
    @StringRes val label: Int,
    val iconResId: Int)
{
    Sales(
        R.string.nav_screen_Sales,
        R.drawable.ic_nav_sale),

    Menu(
        R.string.nav_screen_Menu,
        R.drawable.ic_nav_menu),

    Statistics(
        R.string.nav_screen_Statistics,
        R.drawable.ic_nav_statistic)
}
