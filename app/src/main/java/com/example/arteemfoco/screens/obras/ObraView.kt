package com.example.arteemfoco.screens.obras

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.arteemfoco.R
import com.example.arteemfoco.screens.admin.Obra
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

@Composable
fun ObraViewScreen(navController: NavController, obraId: String) {
    var obra by remember { mutableStateOf<Obra?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }

    // Busca detalhes da obra no Firebase
    LaunchedEffect(obraId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras").document(obraId)
            .get()
            .addOnSuccessListener { document ->
                obra = Obra(
                    id = document.id,
                    title = document.getString("title") ?: "",
                    author = document.getString("author") ?: "",
                    imageUrl = document.getString("imageUrl") ?: "",
                    description = document.getString("description") ?: "",
                )
            }
            .addOnFailureListener { e -> e.printStackTrace() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Cabeçalho com botão "Voltar" e título
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, top = 40.dp)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(30.dp)
                    .clickable { navController.popBackStack() }

            )

            Text(
                text = "Detalhes da Obra",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (obra == null) {
            // Indicador de carregamento enquanto os dados são buscados
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            // Conteúdo principal da tela
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Imagem clicável
                Box(
                    modifier = Modifier
                        .size(300.dp, 200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { isDialogOpen = true }
                ) {
                    AndroidView(
                        factory = { context ->
                            ImageView(context).apply {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                            }
                        },
                        update = { imageView ->
                            Picasso.get()
                                .load(obra!!.imageUrl)
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_foreground)
                                .into(imageView)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Detalhes da obra
                Text(
                    text = obra!!.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = obra!!.author,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = obra!!.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }

    // Diálogo para exibir a imagem em tela cheia
    if (isDialogOpen && obra != null) {
        Dialog(onDismissRequest = { isDialogOpen = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { context ->
                        ImageView(context).apply {
                            scaleType = ImageView.ScaleType.FIT_CENTER
                        }
                    },
                    update = { imageView ->
                        Picasso.get()
                            .load(obra!!.imageUrl)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)
                            .into(imageView)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
