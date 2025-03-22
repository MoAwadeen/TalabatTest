package test.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import test.compose.ui.theme.MyApplicationTheme
import test.compose.view.AppNavigationGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                NavigatePage()
            }
        }

    }
}

@Composable
fun NavigatePage() {
    AppNavigationGraph()
}


class AuthenticationManager {
    private val auth = Firebase.auth

    fun CreateUserWithEmailAndPassword(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        val listener = auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Error(task.exception?.message ?: "Unknown error"))
                }
                close()
            }

        awaitClose {
            // No specific cleanup needed for Firebase auth tasks, but awaitClose is still required.
            // If you have any other listeners or resources to clean up, add them here.
        }
    }

    fun SignInWithEmailAndPassword(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        val listener = auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Error(task.exception?.message ?: "Unknown error"))
                }
                close()
            }

        awaitClose {
            // No specific cleanup needed for Firebase auth tasks, but awaitClose is still required.
            // If you have any other listeners or resources to clean up, add them here.
        }
    }
}

interface AuthResponse {
    data object Success : AuthResponse
    data class Error(val message: String) : AuthResponse
}