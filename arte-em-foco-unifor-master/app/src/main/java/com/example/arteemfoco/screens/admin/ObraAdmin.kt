package com.example.arteemfoco.screens.admin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
    val description: String = ""
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
                .padding(top = 70.dp) // Espaçamento do topo
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
    ObraAdminScreen(navController)
}