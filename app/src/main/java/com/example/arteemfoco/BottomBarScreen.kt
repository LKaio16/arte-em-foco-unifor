package com.example.arteemfoco

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.AutoAwesomeMosaic
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Quiz : BottomBarScreen(
        route = "quizAdmin",
        title = "Quiz",
        icon = Icons.Default.Apps
    )

    object Obras : BottomBarScreen(
        route = "obraAdmin",
        title = "Obras",
        icon = Icons.Default.AutoAwesomeMosaic
    )

//    object Settings : BottomBarScreen(
//        route = "settings",
//        title = "Settings",
//        icon = Icons.Default.Settings
//    )

}
