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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import bottomNavigationItems
import com.example.arteemfoco.screens.admin.ObraAdminScreen
import com.example.arteemfoco.screens.admin.QuizAdminScreen

@Composable
fun MainScreen() {
    val navController =
        rememberNavController() // Certifique-se de que isso não está dentro de um bloco que causa recomposições frequentes

//    SetupNavGraph(navController = navController)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
    ) { innerPadding ->
        SetupNavGraph2(navController, modifier = Modifier.padding(innerPadding))
    }
}

//
//) { innerPadding ->
//    // Aqui, o 'innerPadding' deve ser passado para o conteúdo
//    BottomNavGraph(
//        navController = navController,
//        modifier = Modifier.padding(innerPadding) // Usando o 'padding' fornecido pelo Scaffold
//    )
//}


@Composable
fun AdminScreenScaffold(navController: NavHostController, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavigationItems.forEach { item ->
                    if (item.route == Screen.ObraAdmin.route || item.route == Screen.QuizAdmin.route) {
                        NavigationBarItem(
                            selected = navController.currentDestination?.route == item.route,
                            onClick = { navController.navigate(item.route) },
                            label = { Text(item.title) },
                            icon = { Icon(item.icon, contentDescription = item.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

