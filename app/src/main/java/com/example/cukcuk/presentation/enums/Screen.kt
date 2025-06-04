package com.example.cukcuk.presentation.enums

import com.example.cukcuk.R

enum class Screen(val displayName: String, val iconResId: Int) {
    Sales("Bán hàng", R.drawable.ic_nav_sale),
    Menu("Thực đơn", R.drawable.ic_nav_menu),
    Statistics("Doanh thu", R.drawable.ic_nav_statistic)
}
