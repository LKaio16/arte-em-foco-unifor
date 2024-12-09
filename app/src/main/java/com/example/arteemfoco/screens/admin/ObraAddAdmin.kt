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
import androidx.compose.material3.*
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObraAddAdminScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var audioDescription by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Obra") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },

            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable { selectImageLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Imagem Selecionada",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Clique para adicionar uma imagem", textAlign = TextAlign.Center)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Autor
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Descrição
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Audio-Descrição
            OutlinedTextField(
                value = audioDescription,
                onValueChange = { audioDescription = it },
                label = { Text("Audio-Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (imageUri != null) {
                        isUploading = true
                        val storageRef = FirebaseStorage.getInstance().reference
                        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

                        imageRef.putFile(imageUri!!)
                            .addOnSuccessListener {
                                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                                    val obra = hashMapOf(
                                        "title" to title,
                                        "author" to author,
                                        "description" to description,
                                        "audioDescription" to audioDescription,
                                        "imageUrl" to downloadUrl.toString()
                                    )
                                    FirebaseFirestore.getInstance().collection("obras").add(obra)
                                        .addOnSuccessListener {
                                            Log.d("Firestore", "Obra adicionada com sucesso!")
                                            navController.popBackStack()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.w("Firestore", "Erro ao adicionar obra", e)
                                        }
                                }
                            }
                            .addOnFailureListener { Log.e("Firebase", "Erro ao fazer upload", it) }
                            .addOnCompleteListener { isUploading = false }
                    }
                },
                enabled = !isUploading,
                modifier = Modifier.fillMaxWidth(),

            ) {
                Text(
                    text = if (isUploading) "Salvando..." else "Adicionar Obra",
                    color = if (isUploading) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary
                )

            }
        }
    }
}

@Preview
@Composable
fun ObraAddAdminScreenPreview() {
    ObraAddAdminScreen(navController = rememberNavController())
}
