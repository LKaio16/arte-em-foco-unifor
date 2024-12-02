package com.example.arteemfoco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arteemfoco.ui.theme.ArteEmFocoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ArteEmFocoTheme {
                // Crie e configure o NavController aqui
                val navController = rememberNavController()

                // Deve-se definir o ViewModelStore antes de configurar o NavGraph
                navController.setViewModelStore(viewModelStore)

                // Somente após configurar o ViewModelStore, configure o NavGraph
                SetupNavGraph(navController = navController)
            }
        }
    }
}





@Composable
fun CaixaTexto(
    placeholder: String,
    text: String,
    onTextChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(250.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(45.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .width(250.dp)
                .background(Color.Transparent),
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            ),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp
                    )
                }
                innerTextField()
            },
            singleLine = true
        )
    }
}



@Composable
fun LabelInput(text: String) {
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text)
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

//
//@Preview
//@Composable
//private fun MainScreenPreview() {
//    MainScreen()
//}

