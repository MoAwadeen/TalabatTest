package test.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import kotlinx.coroutines.launch
import test.compose.AuthResponse
import test.compose.AuthenticationManager
import test.compose.components.BasicButton
import test.compose.components.BoldTextComponent
import test.compose.components.ClickableTextComponent
import test.compose.components.DifferentSizeTextComponent
import test.compose.components.GoogleSignInButton
import test.compose.components.OutlinedTextFieldConfirmPassword
import test.compose.components.OutlinedTextFieldEmail
import test.compose.components.OutlinedTextFieldName
import test.compose.components.OutlinedTextFieldPasswordSignUp

@Composable
fun RegisterScreen(navController: NavController?) {
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
            .padding(28.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))

            BoldTextComponent(value = "Create Account")

            DifferentSizeTextComponent(
                value = "Create an account so you can explore \nall the existing jobs",
                size = 16.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextFieldName(label = "First name", value = firstName, onValueChange = { firstName = it })

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextFieldName(label = "Last name", value = lastName, onValueChange = { lastName = it })

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextFieldEmail(
                label = "Email Address",
                text = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextFieldPasswordSignUp(label = "Password", password = password, onPasswordChange = { password = it })

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextFieldConfirmPassword(
                label = "Confirm Password",
                password = password,
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = { confirmPassword = it }
            )

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                BoldTextComponent(value = errorMessage)
            }

            Spacer(modifier = Modifier.height(14.dp))

            BasicButton(
                label = "Sign up",
                onClick = {
                    if (password != confirmPassword) {
                        errorMessage = "Passwords do not match!"
                        return@BasicButton
                    }
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Email and password cannot be empty"
                        return@BasicButton
                    }

                    coroutineScope.launch {
                        authManager.CreateUserWithEmailAndPassword(email, password).collect { response ->
                            when (response) {
                                is AuthResponse.Success -> {
                                    val userId = auth.currentUser?.uid
                                    if (userId != null) {
                                        saveUserDataToFireStore(userId, firstName, lastName, email)
                                        navController?.navigate(Routes.SECOND_REGISTER)
                                    }
                                }
                                is AuthResponse.Error -> errorMessage = response.message
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            ClickableTextComponent(
                value = "Already have an account",
                onClick = {
                    navController?.let {
                        if (it.currentDestination?.route != Routes.LOGIN) {
                            it.navigate(Routes.LOGIN)
                        }
                    } ?: run {
                        errorMessage = "Navigation error: navController is null"
                    }
                }
            )

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
fun RegisterScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize().background(Color.White),
    ) {
        RegisterScreen(rememberNavController())
    }
}
