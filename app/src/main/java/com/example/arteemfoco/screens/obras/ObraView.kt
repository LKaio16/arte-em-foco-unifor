package com.example.arteemfoco.screens.obras

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import java.util.Locale

@Composable
fun ObraViewScreen(navController: NavController, obraId: String) {
    var obra by remember { mutableStateOf<Obra?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var textToSpeech by remember { mutableStateOf<TextToSpeech?>(null) }

    // Inicializar TTS
    LaunchedEffect(Unit) {
        textToSpeech = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                val locale = Locale("pt", "BR")
                textToSpeech?.setLanguage(locale)
            }
        }
    }

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
                    description = document.getString("description") ?: "" // Certifique-se de que tenha o campo description
                )
            }
            .addOnFailureListener { e -> e.printStackTrace() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Botão Voltar
        Box(
            modifier = Modifier
                .padding(start = 17.dp, top = 40.dp)
                .clickable { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.Black
            )
        }

        // Título centralizado
        Text(
            text = "Detalhes da Obra",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            textAlign = TextAlign.Center
        )

        // Conteúdo da página
        if (obra == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // Imagem dentro do Box cinza com clique
                Box(
                    modifier = Modifier
                        .background(Color.Gray)
                        .size(350.dp, 250.dp)
                        .clickable { isDialogOpen = true }
                ) {
                    AndroidView(
                        factory = { context ->
                            ImageView(context).apply {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                adjustViewBounds = false
                            }
                        },
                        update = { imageView ->
                            Picasso.get()
                                .load(obra!!.imageUrl)
                                .placeholder(R.drawable.ic_launcher_background) // Imagem de carregamento
                                .error(R.drawable.ic_launcher_foreground)       // Imagem de erro
                                .into(imageView)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Gray)
                            .clipToBounds() // Garante que o conteúdo seja cortado no tamanho da Box
                    )
                }

                Spacer(Modifier.height(20.dp))

                // Informações da obra
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = obra!!.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = obra!!.author,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = obra!!.description,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(Modifier.height(20.dp))

                // Botão para ativar o TTS
                Button(
                    onClick = {
                        speakDetails(textToSpeech, obra!!)
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Ouvir Detalhes")
                }
            }
        }
    }

    // Diálogo para exibir imagem em tela cheia
    if (isDialogOpen && obra != null) {
        Dialog(onDismissRequest = { isDialogOpen = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { context -> ImageView(context).apply { scaleType = ImageView.ScaleType.FIT_CENTER } },
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

// Função para falar os detalhes da obra (Título, Autor, Descrição)
fun speakDetails(tts: TextToSpeech?, obra: Obra) {
    val textToSpeak = buildString {
        append("Título: ${obra.title}")
        append("\nAutor: ${obra.author}")
        append("\nDescrição: ${obra.description}")
    }
    tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
}

@Preview
@Composable
fun ObraViewScreenPreview() {
    val navController = rememberNavController()
    ObraViewScreen(navController = navController, obraId = "exampleId")
}
