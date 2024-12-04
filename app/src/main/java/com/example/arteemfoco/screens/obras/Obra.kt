package com.example.arteemfoco.screens.obras

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import android.widget.ImageView
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.draw.clip
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.arteemfoco.AdminScreenScaffold
import com.example.arteemfoco.R
import com.example.arteemfoco.Screen
import com.example.arteemfoco.screens.admin.Obra
import com.example.arteemfoco.screens.admin.ObraCardAdmin
import com.squareup.picasso.Picasso


@Composable
fun ObraScreen(navController: NavHostController) {

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
                  // Aplicar o padding fornecido pelo Scaffold aqui
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
                        ObraCard(
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

        }
    }


@Composable
@Preview
fun ObraScreenPreview() {
    val navController = rememberNavController()
    ObraScreen(navController)
}


@Composable
fun ObraCard(
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
                navController.navigate("obraView/$obraId")
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

    }
}

