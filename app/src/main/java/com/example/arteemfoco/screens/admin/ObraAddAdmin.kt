package com.example.arteemfoco.screens.admin

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import java.util.*

@Composable
fun ObraAddAdminScreen(navController: NavController) {

    // Estado para os campos de entrada
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var audioDescription by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    // Lançador para selecionar imagens
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

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
            // Box para seleção de imagem
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .size(300.dp, 150.dp)
                    .clickable {
                        selectImageLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Imagem Selecionada",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "Clique para adicionar uma imagem",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

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
                    if (imageUri != null) {
                        isUploading = true
                        val storageRef = FirebaseStorage.getInstance().reference
                        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

                        imageRef.putFile(imageUri!!)
                            .addOnSuccessListener {
                                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                                    val db = FirebaseFirestore.getInstance()
                                    val obra = hashMapOf(
                                        "title" to title,
                                        "author" to author,
                                        "description" to description,
                                        "audioDescription" to audioDescription,
                                        "imageUrl" to downloadUrl.toString()
                                    )

                                    db.collection("obras").add(obra)
                                        .addOnSuccessListener {
                                            Log.d("Firestore", "Obra adicionada com sucesso!")
                                            navController.popBackStack()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("Firestore", "Erro ao adicionar obra", e)
                                        }
                                }
                            }
                            .addOnFailureListener {
                                Log.e("Firebase", "Erro ao fazer upload da imagem", it)
                            }
                            .addOnCompleteListener {
                                isUploading = false
                            }
                    } else {
                        Log.e("Firebase", "Nenhuma imagem selecionada")
                    }
                },
                enabled = !isUploading
            ) {
                Text(text = if (isUploading) "Salvando..." else "Adicionar Obra")
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
