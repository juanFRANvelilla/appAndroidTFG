package com.example.apptfgandroid.ui.screens.mainMenu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.apptfgandroid.navigation.AppScreens

sealed class ItemBottomNav(
    val icon: ImageVector,
    val title: String,
    val route: String
) {
    object MainMenuHome: ItemBottomNav(
        icon = Icons.Outlined.Home,
        title = "Home",
        route = AppScreens.MainMenu.route
    )
    object Contacts: ItemBottomNav(
        icon = Icons.Outlined.AccountBox,
        title = "Home",
        route = AppScreens.ManageContacs.route
    )
    object CurrentDebts: ItemBottomNav(
        icon = Icons.Outlined.List,
        title = "Home",
        route = AppScreens.MainMenu.route
    )
}