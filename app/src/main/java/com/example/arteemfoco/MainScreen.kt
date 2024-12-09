package com.example.arteemfoco


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
import bottomNavigationItems


@Composable
fun MainScreen() {
    val navController =
        rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
    ) { innerPadding ->
        SetupAdminNavGraph(navController, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun AdminScreenScaffold(navController: NavHostController, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary, // Cor do fundo do NavigationBar
                contentColor = MaterialTheme.colorScheme.primary // Cor do pconteúdo do NavigationBar
            ) {
                bottomNavigationItems.forEach { item ->
                    if (item.route == Screen.ObraAdmin.route || item.route == Screen.QuizAdmin.route) {
                        NavigationBarItem(
                            selected = navController.currentDestination?.route == item.route,
                            onClick = { navController.navigate(item.route) },
                            label = { Text(item.title) },
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            colors = NavigationBarItemColors(
                                selectedIconColor = MaterialTheme.colorScheme.primaryContainer, // Cor do ícone selecionado
                                selectedTextColor = MaterialTheme.colorScheme.primaryContainer, // Cor do texto selecionado
                                selectedIndicatorColor = MaterialTheme.colorScheme.primary, // Cor do indicador selecionado
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface, // Cor do ícone não selecionado
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface, // Cor do texto não selecionado
                                disabledIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f), // Cor do ícone desabilitado
                                disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // Cor do texto desabilitado
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
