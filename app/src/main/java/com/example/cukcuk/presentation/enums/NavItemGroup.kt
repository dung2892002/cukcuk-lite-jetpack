package com.example.cukcuk.presentation.enums
import com.example.cukcuk.R

enum class NavItemGroup(
    val label: String,
    val items: List<NavItem>
) {
    Setting(
        label = "Thiết lập",
        items = listOf(
            NavItem.SynchronizeData,
            NavItem.Setting,
            NavItem.LinkAccount,
        )
    ),
    Help(
        label = "Trợ giúp",
        items = listOf(
            NavItem.Notification,
            NavItem.SharedWithFriend,
            NavItem.RatingApp,
            NavItem.SuggestApp,
            NavItem.AppInfo,
        )
    ),
    Account(
        label = "Tài khoản",
        items = listOf(
            NavItem.SetPassword,
            NavItem.Logout,
        )
    )
}

enum class NavItem(
    val label: String,
    val iconResId: Int,
){
    SynchronizeData(
        label = "Đồng bộ dữ liệu",
        iconResId = R.drawable.ic_nav_sync_data
    ),
    Setting(
        label = "Thiết lập",
        iconResId = R.drawable.ic_nav_setting
    ),
    LinkAccount(
        label = "Liên kết tài khoản",
        iconResId = R.drawable.ic_nav_link_account
    ),
    Notification(
        label = "Thông báo",
        iconResId = R.drawable.ic_nav_notification
    ),
    SharedWithFriend(
        label = "Giới thiệu cho bạn",
        iconResId = R.drawable.ic_nav_share
    ),
    RatingApp(
        label = "Đánh giá ứng dụng",
        iconResId = R.drawable.ic_nav_rating
    ),
    SuggestApp(
        label = "Góp ý với nhà phát triển",
        iconResId = R.drawable.ic_nav_suggestion
    ),
    AppInfo(
        label = "Thông tin sản phẩm",
        iconResId = R.drawable.ic_nav_app_info
    ),
    SetPassword(
        label = "Đặt mật khẩu",
        iconResId = R.drawable.ic_nav_password
    ),
    Logout(
        label = "Đăng xuất",
        iconResId = R.drawable.ic_nav_logout
    )
}