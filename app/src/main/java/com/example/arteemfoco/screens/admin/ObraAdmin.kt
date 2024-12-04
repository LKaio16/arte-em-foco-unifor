package com.example.arteemfoco.screens.admin

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.arteemfoco.AdminScreenScaffold
import com.example.arteemfoco.R
import com.example.arteemfoco.Screen
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlin.math.log

data class Obra(
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val imageUrl: String = "",
    var description: String = "",
)


@Composable
fun ObraAdminScreen(navController: NavHostController) {
    AdminScreenScaffold(navController) { innerPadding ->
        val db = FirebaseFirestore.getInstance()
        var obras by remember { mutableStateOf(listOf<com.example.arteemfoco.screens.admin.Obra>()) }
        var isLoading by remember { mutableStateOf(true) }

        // Busca as obras no Firestore
        LaunchedEffect(Unit) {
            db.collection("obras")
                .get()
                .addOnSuccessListener { result ->
                    val obrasList = result.map { document ->
                        com.example.arteemfoco.screens.admin.Obra(
                            id = document.id,
                            title = document.getString("title") ?: "",
                            author = document.getString("author") ?: "",
                            imageUrl = document.getString("imageUrl") ?: ""
                        )
                    }
                    obras = obrasList
                    isLoading = false
                }
                .addOnFailureListener { exception ->
                    Log.w("Firestore", "Erro ao buscar obras: ", exception)
                }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)  // Aplicar o padding fornecido pelo Scaffold aqui
        ) {
            Text(
                text = "Obra",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp) // Espaçamento do topo
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading) {
                    CircularProgressIndicator()  // Mostra o indicador de carregamento
                } else {
                    obras.forEach { obra ->
                        ObraCardAdmin(
                            title = obra.title,
                            author = obra.author,
                            obraId = obra.id,
                            onDelete = { obraId ->
                                obras = obras.filter { it.id != obraId }
                            },
                            navController = navController,
                            imageUrl = obra.imageUrl
                        )
                        Spacer(Modifier.height(10.dp))
                    }

                    Spacer(Modifier.height(20.dp))
                }
            }
            // Botão flutuante deve ser colocado fora da Column para evitar que seja empurrado pela lista
            FloatingActionButton(
                onClick = { navController.navigate(Screen.ObraAddAdmin.route) },
                backgroundColor = Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        bottom = 16.dp,
                        end = 16.dp
                    )  // Ajuste este padding para evitar a BottomBar
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }

        }
    }
}


@Composable
@Preview
fun ObraAdminScreenPreview() {
    val navController = rememberNavController()
//    ObraCardAdmin(
//        "afaaaaaaaaaaaaaa",
//        "asdasdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//    )
}


@Composable
fun ObraCardAdmin(
    title: String,
    author: String,
    obraId: String,
    imageUrl: String?,  // Parâmetro para a URL da imagem
    onDelete: (String) -> Unit,
    navController: NavController,
) {
    val db = FirebaseFirestore.getInstance()

    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.Gray, shape = RoundedCornerShape(16.dp))
            .height(120.dp)
            .clickable {
                navController.navigate("obraAdminView/$obraId")
            }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Caixa à esquerda (imagem ou fundo azul)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(140.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)) // Recorte arredondado
                    .background(Color.Blue)
            ) {

                val imageUrlToLoad =
                    imageUrl.takeIf { !it.isNullOrEmpty() }
                        ?: "https://www.animeac.com.br/wp-content/uploads/2024/11/Descubra-Quem-e-Aira-Shiratori-em-Dandadan.png"

                AndroidView(
                    factory = { context ->
                        ImageView(context).apply {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            clipToOutline = true // Garante que o recorte será aplicado
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    update = { imageView ->
                        Picasso.get()
                            .load(imageUrlToLoad)
                            .placeholder(R.drawable.ic_launcher_background) // Imagem de carregamento
                            .error(R.drawable.ic_launcher_foreground)       // Imagem de erro
                            .into(imageView)
                    }
                )
            }
            // Coluna com título e subtítulo à direita
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 35.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = title, fontSize = 15.sp, color = Color.White)
                Spacer(Modifier.height(2.dp))
                Text(
                    text = author,
                    fontSize = 11.sp,
                    color = Color.White,
                    modifier = Modifier.width(170.dp)
                )
            }
        }

        // Ícone de exclusão
        Box(
            modifier = Modifier
                .padding(end = 20.dp, top = 8.dp, start = 5.dp)
                .align(Alignment.CenterEnd)
                .clickable {
                    // Exclui a obra do Firestore
                    db
                        .collection("obras")
                        .document(obraId)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("Firestore", "Obra excluída com sucesso!")
                            onDelete(obraId) // Atualiza a lista de obras na tela
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Erro ao excluir obra", e)
                        }
                }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}
