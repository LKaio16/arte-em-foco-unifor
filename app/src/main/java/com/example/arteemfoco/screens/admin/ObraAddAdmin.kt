package com.example.arteemfoco.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import com.example.arteemfoco.screens.obras.Obra

@Composable
fun ObraAddAdminScreen(navController: NavController) {

    // Estado para os campos de entrada
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var audioDescription by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Ícone de voltar no canto superior esquerdo
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

        // Texto "Salvar" no canto superior direito
        Text(
            text = "Salvar",
            fontSize = 16.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 37.dp, end = 17.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Text(
            text = "Nova Obra",
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp)
        ) {
            // Imagem
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .size(300.dp, 150.dp)
            )


            Spacer(Modifier.height(16.dp))

            // Campo de Título
            Text(text = "Título", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 56.dp),
                placeholder = { Text("Digite o título") }
            )

            Spacer(Modifier.height(16.dp))

            // Campo de Autor
            Text(text = "Autor", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = author,
                onValueChange = { author = it },
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 56.dp),
                placeholder = { Text("Digite o autor") }
            )

            Spacer(Modifier.height(16.dp))

            // Campo de Descrição
            Text(text = "Descrição", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 56.dp),
                placeholder = { Text("Digite a descrição") }
            )

            Spacer(Modifier.height(16.dp))

            // Campo de Audio-Descrição
            Text(text = "Audio-Descrição", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            TextField(
                value = audioDescription,
                onValueChange = { audioDescription = it },
                modifier = Modifier
                    .background(Color.LightGray)
                    .size(300.dp, 100.dp),
                placeholder = { Text("Digite a audio-descrição") }
            )
        }

        // Botão de "Adicionar Obra"
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp, end = 20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Button(
                onClick = {
                    val db = FirebaseFirestore.getInstance()
                    val obra = hashMapOf(
                        "title" to title,
                        "author" to author,
                        "description" to description,
                        "audioDescription" to audioDescription
                    )

                    db.collection("obras").add(obra)
                        .addOnSuccessListener { documentReference ->
                            Log.d("Firestore", "Obra adicionada com ID: ${documentReference.id}")

                            title = ""
                            author = ""
                            description = ""
                            audioDescription = ""

                            navController.popBackStack() // Navega de volta após adicionar
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Erro ao adicionar obra", e)
                        }
                }
            ) {
                Text(text = "Adicionar Obra")
            }
        }
    }
}

@Composable
@Preview
fun ObraAddAdminScreenPreview() {
    val navController = rememberNavController()
    ObraAddAdminScreen(navController)
}
