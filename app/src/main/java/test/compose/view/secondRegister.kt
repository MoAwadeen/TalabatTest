package test.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import test.compose.AuthenticationManager
import test.compose.components.BasicButton
import test.compose.components.BoldTextComponent
import test.compose.components.DifferentSizeTextComponent
import test.compose.components.TwoItemsRow

@Composable
fun SecondRegisterScreen(navController: NavController?) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val authManager = AuthenticationManager()
    val auth = FirebaseAuth.getInstance()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))

            BoldTextComponent(value = "Sign Up")

            TwoItemsRow(icon = Icons.Filled.PersonOutline, label = "What's your gender ?")

            TwoItemsRow(icon = Icons.Filled.Analytics, label = "What's your level ?")

            BasicButton(label = "Finish", onClick = { navController?.navigate(Routes.HOME) })

        }
    }
}

private fun saveUserDataToFireStore(userId: String, firstName: String, lastName: String, email: String) {
    val firestore = FirebaseFirestore.getInstance()
    val userData = hashMapOf(
        "firstName" to firstName,
        "lastName" to lastName,
        "email" to email
    )

    firestore.collection("users").document(userId)
        .set(userData)
        .addOnSuccessListener {
            println("User data saved successfully")
        }
        .addOnFailureListener { e ->
            println("Error saving user data: $e") }
}

@Preview
@Composable
fun SecondRegisterScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize().background(Color.White),
    ) {
        SecondRegisterScreen(rememberNavController())
    }
}
