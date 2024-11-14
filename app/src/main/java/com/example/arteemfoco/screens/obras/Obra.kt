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

data class Obra(
    val title: String = "",
    val description: String = ""
)

@Composable
fun ObraScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    var obras by remember { mutableStateOf(listOf<Obra>()) }

    // Busca as obras no Firestore
    LaunchedEffect(Unit) {
        db.collection("obras")
            .get()
            .addOnSuccessListener { result ->
                val obrasList = result.map { document ->
                    Obra(
                        title = document.getString("title") ?: "",
                        description = document.getString("description") ?: ""
                    )
                }
                obras = obrasList
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Erro ao buscar obras: ", exception)
            }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier.padding(start = 17.dp, top = 37.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black,
                modifier = Modifier
                    .clickable { navController.popBackStack() }
            )
        }

        Text(
            text = "Obras",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            textAlign = TextAlign.Center
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            obras.forEach { obra ->
                ObraCard(title = obra.title, subtitle = obra.description)
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(20.dp))
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
fun ObraCard(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .background(Color.Gray, shape = RoundedCornerShape(16.dp))
            .height(120.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Caixa escura à esquerda
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(140.dp)
                    .background(
                        Color.DarkGray,
                        shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                    )
            )

            // Coluna com título e subtítulo à direita
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = title, fontSize = 15.sp, color = Color.White)
                Spacer(Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 11.sp, color = Color.White, modifier = Modifier.width(170.dp))
            }
        }
    }
}
