package test.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import test.compose.AuthResponse
import test.compose.AuthenticationManager
import test.compose.components.*
import test.compose.ui.theme.Bg

@Composable
fun RegisterScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val authManager = AuthenticationManager()

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
                value = "Create an account so you can explore all the\nexisting jobs",
                size = 16.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            OutlinedTextFieldName(label = "First name", value = firstName, onValueChange = { firstName = it })

            Spacer(modifier = Modifier.height(29.dp))

            OutlinedTextFieldName(label = "Last name", value = lastName, onValueChange = { lastName = it })

            Spacer(modifier = Modifier.height(29.dp))

            OutlinedTextFieldEmail(
                label = "Email Address",
                text = email,
                onValueChange = { email = it }  // Ensure state updates
            )

            Spacer(modifier = Modifier.height(29.dp))

            OutlinedTextFieldPasswordSignUp(label = "Password", password = password, onPasswordChange = { password = it })

            Spacer(modifier = Modifier.height(29.dp))

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

            Spacer(modifier = Modifier.height(29.dp))

            BasicButton(
                label = "Sign up",
                onClick = {
                    coroutineScope.launch {
                        authManager.CreateUserWithEmailAndPassword(email, password).collect { response ->
                            when (response) {
                                is AuthResponse.Success -> navController.navigate(Routes.LOGIN)
                                is AuthResponse.Error -> errorMessage = response.message
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(29.dp))

            ClickableTextComponent(
                value = "Already have an account",
                onClick = {
                    if (navController.currentDestination?.route != Routes.LOGIN) {
                        navController.navigate(Routes.LOGIN)
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize().background(Bg),
    ) {
        RegisterScreen(rememberNavController())
    }
}
