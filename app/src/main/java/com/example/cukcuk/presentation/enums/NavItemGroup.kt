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
    Other(
        label = "Khác",
        items = listOf(
            NavItem.TestApi,
        )
    ),
    Account(
        label = "Tài khoản",
        items = listOf(
            NavItem.SetPassword,
            NavItem.Logout,
        )
    ),
}

enum class NavItem(
    val label: String,
    val iconResId: Int,
    val route: String = "null"
){
    SynchronizeData(
        label = "Đồng bộ dữ liệu",
        iconResId = R.drawable.ic_nav_sync_data,
        route = "synchronize"
    ),
    Setting(
        label = "Thiết lập",
        iconResId = R.drawable.ic_nav_setting,
        route = "setting",
    ),
    LinkAccount(
        label = "Liên kết tài khoản",
        iconResId = R.drawable.ic_nav_link_account,
        route = "link_account"
    ),
    Notification(
        label = "Thông báo",
        iconResId = R.drawable.ic_nav_notification,
        route = "notification"
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
        iconResId = R.drawable.ic_nav_suggestion,
        route = "feedback"
    ),
    AppInfo(
        label = "Thông tin sản phẩm",
        iconResId = R.drawable.ic_nav_app_info,
        route = "app_info"
    ),
    SetPassword(
        label = "Đặt mật khẩu",
        iconResId = R.drawable.ic_nav_password,
        route = "set_password"
    ),
    Logout(
        label = "Đăng xuất",
        iconResId = R.drawable.ic_nav_logout,
        route = "login"
    ),
    TestApi(
        label = "Product API",
        iconResId = R.drawable.ic_nav_logout,
        route = "product_api"
    )
}