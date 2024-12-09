package com.example.arteemfoco


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import bottomNavigationItems


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        AdminScreenScaffold(
            navController = navController,
            content = { SetupAdminNavGraph(navController, modifier = Modifier.padding(innerPadding)) }
        )
    }
}

@Composable
fun AdminScreenScaffold(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route

    val showBottomBar = currentDestination in listOf(
        Screen.ObraAdmin.route,
        Screen.QuizAdmin.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.primary) {
                    bottomNavigationItems.forEach { item ->
                        if (item.route == Screen.ObraAdmin.route || item.route == Screen.QuizAdmin.route) {
                            NavigationBarItem(
                                selected = currentDestination == item.route,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                label = { Text(item.title) },
                                icon = { Icon(item.icon, contentDescription = item.title) },
                                colors = NavigationBarItemColors(
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                                    unselectedTextColor = MaterialTheme.colorScheme.outline,
                                    disabledIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                                )
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)

        // Intercepta o botão "voltar"
        BackHandler {
            if (currentDestination == Screen.QuizAdmin.route) {
                // Vai para StartScreen e remove todas as rotas
                navController.navigate("startScreen") {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                }
            } else {
                // Comportamento padrão de voltar
                navController.popBackStack()
            }
        }
    }
}
