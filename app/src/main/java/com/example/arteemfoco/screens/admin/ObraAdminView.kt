package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ObraAdminViewScreen(navController: NavController, obraId: String) {
    var obra by remember { mutableStateOf<Obra?>(null) }

    // Fetch detalhes da obra do Firebase
    LaunchedEffect(obraId) {
        val db = FirebaseFirestore.getInstance()
        db.collection("obras").document(obraId)
            .get()
            .addOnSuccessListener { document ->
                obra = Obra(
                    id = document.id,
                    title = document.getString("title") ?: "",
                    author = document.getString("author") ?: ""
                )
            }
            .addOnFailureListener { e ->
                // Log de erro caso necessário
                e.printStackTrace()
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes da Obra") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            if (obra == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Título",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = obra!!.title,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Autor",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = obra!!.author,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ObraAdminViewScreenPreview() {
    val navController = rememberNavController()
    ObraAdminViewScreen(navController = navController, obraId = "exampleId")
}
