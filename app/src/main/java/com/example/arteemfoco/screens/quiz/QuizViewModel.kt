import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arteemfoco.screens.admin.Question
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Quiz(
    val id: String = "",
    val title: String = "",
    val perguntas: List<Question> = emptyList()
)

class QuizViewModel : ViewModel() {
    private val _quiz = MutableStateFlow<Quiz?>(null)
    val quiz: StateFlow<Quiz?> = _quiz

    private val firestore = FirebaseFirestore.getInstance()

    fun loadQuiz(code: String) {
        viewModelScope.launch {
            // Buscar o quiz pela propriedade 'code'
            firestore.collection("quizzes")
                .whereEqualTo("code", code) // Procurando pelo campo 'code' no Firestore
                .get()
                .addOnSuccessListener { querySnapshot ->
                    // Verifica se encontrou algum quiz com o código
                    val document = querySnapshot.documents.firstOrNull()

                    if (document != null) {
                        val quizId = document.id  // O ID completo do quiz
                        val title = document.getString("title") ?: "Sem título"

                        // Agora, buscando as perguntas do quiz
                        val perguntasCollection = firestore.collection("quizzes")
                            .document(quizId)
                            .collection("perguntas")

                        perguntasCollection.get().addOnSuccessListener { perguntasSnapshot ->
                            val perguntas = perguntasSnapshot.map { perguntaDoc ->
                                Question(
                                    title = perguntaDoc.getString("title") ?: "Pergunta",
                                    description = perguntaDoc.getString("description") ?: "Descrição",
                                    alternatives = perguntaDoc.get("alternatives") as List<String>,
                                    correctAnswerIndex = perguntaDoc.getLong("correctAnswerIndex")?.toInt() ?: -1
                                )
                            }

                            // Atualizando o estado com o quiz completo
                            _quiz.value = Quiz(id = quizId, title = title, perguntas = perguntas)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Tratar erro de falha na busca
                    println("Erro ao buscar quiz: ${exception.message}")
                }
        }
    }
}


