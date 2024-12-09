import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.AutoAwesomeMosaic
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.AutoAwesomeMosaic
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Quiz",
        icon = Icons.Filled.AutoAwesomeMosaic,
        route = "quizAdminScreen"
    ),

    BottomNavigationItem(
        title = "Obras",
        icon = Icons.Filled.Apps,
        route = "obraAdminScreen"
    )

)
