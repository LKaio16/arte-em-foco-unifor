package com.example.arteemfoco.screens.admin

import android.util.Log
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.arteemfoco.screens.obras.Obra
import com.example.arteemfoco.screens.obras.ObraCard
import com.google.firebase.firestore.FirebaseFirestore

data class Obra(
    val title: String = "",
    val description: String = "",
)


@Composable
fun ObraAdminScreen(navController: NavController) {
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
            .background(Color.White) // Fundo branco para toda a tela
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
            obras.forEach { obra ->
                ObraCardAdmin(title = obra.title, subtitle = obra.description)
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(20.dp))
        }

        FloatingActionButton(
            onClick = { navController.navigate("obraAddAdminScreen") },
            backgroundColor = Color.Gray,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 50.dp, end = 20.dp) // Padding para afastar do canto
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }

    }
}

@Composable
@Preview
fun ObraAdminScreenPreview() {
    val navController = rememberNavController()
    ObraCardAdmin("af", "asdasd")
}


@Composable
fun ObraCardAdmin(title: String, subtitle: String) {
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
                        Color.Red,
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
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = Color.White,
                    modifier = Modifier.width(170.dp)
                )
            }



        }

        Box(
            modifier = Modifier
                .background(color = Color.Red)
                .padding(end = 8.dp, top = 8.dp, start = 5.dp)
                .align(Alignment.TopEnd)

        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }

    }
}