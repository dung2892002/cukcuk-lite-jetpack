package com.example.presentation.enums
import androidx.annotation.StringRes
import com.example.presentation.R

enum class NavItemGroup(
    @StringRes val label: Int,
    val items: List<NavItem>
) {
    Setting(
        label = R.string.nav_item_group_Setting,
        items = listOf(
            NavItem.SynchronizeData,
            NavItem.Language,
            NavItem.Setting,
            NavItem.LinkAccount,
        )
    ),
    Help(
        label = R.string.nav_item_group_Help,
        items = listOf(
            NavItem.Notification,
            NavItem.SharedWithFriend,
            NavItem.RatingApp,
            NavItem.SuggestApp,
            NavItem.AppInfo,
        )
    ),
    Other(
        label = R.string.nav_item_group_Other,
        items = listOf(
            NavItem.TestApi,
        )
    ),
    Account(
        label = R.string.nav_item_group_Account,
        items = listOf(
            NavItem.SetPassword,
            NavItem.Logout,
        )
    ),
}

enum class NavItem(
    @StringRes val label: Int,
    val iconResId: Int,
    val route: String = "null"
){
    SynchronizeData(
        label = R.string.nav_item_SynchronizeData,
        iconResId = R.drawable.ic_nav_sync_data,
        route = "synchronize"
    ),
    Setting(
        label = R.string.nav_item_Setting,
        iconResId = R.drawable.ic_nav_setting,
        route = "setting",
    ),
    LinkAccount(
        label = R.string.nav_item_LinkAccount,
        iconResId = R.drawable.ic_nav_link_account,
        route = "link_account"
    ),
    Notification(
        label = R.string.nav_item_Notification,
        iconResId = R.drawable.ic_nav_notification,
        route = "notification"
    ),
    SharedWithFriend(
        label = R.string.nav_item_SharedWithFriend,
        iconResId = R.drawable.ic_nav_share
    ),
    RatingApp(
        label = R.string.nav_item_RatingApp,
        iconResId = R.drawable.ic_nav_rating
    ),
    SuggestApp(
        label = R.string.nav_item_SuggestApp,
        iconResId = R.drawable.ic_nav_suggestion,
        route = "feedback"
    ),
    AppInfo(
        label = R.string.nav_item_AppInfo,
        iconResId = R.drawable.ic_nav_app_info,
        route = "app_info"
    ),
    SetPassword(
        label = R.string.nav_item_SetPassword,
        iconResId = R.drawable.ic_nav_password,
        route = "set_password"
    ),
    Logout(
        label = R.string.nav_item_Logout,
        iconResId = R.drawable.ic_nav_logout,
        route = "login"
    ),
    TestApi(
        label = R.string.nav_item_TestApi,
        iconResId = R.drawable.ic_nav_logout,
        route = "product_api"
    ),

    Language(
        label = R.string.nav_item_Language,
        iconResId = R.drawable.ic_language,
        route = "language"
    )
}