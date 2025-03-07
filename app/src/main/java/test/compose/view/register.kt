package test.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import test.compose.components.*
import test.compose.ui.theme.Bg

@Composable
fun RegisterScreen(navController: NavController) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(28.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(97.dp))

            BoldTextComponent(value = "Create Account")

            DifferentSizeTextComponent(
                value = "Create an account so you can explore all the\nexisting jobs",
                size = 16.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            OutlinedTextFieldName(label = "First name")

            Spacer(modifier = Modifier.height(29.dp))

            OutlinedTextFieldName(label = "Last name")

            Spacer(modifier = Modifier.height(29.dp))

            OutlinedTextFieldEmail(label = "E-mail")

            Spacer(modifier = Modifier.height(29.dp))

            OutlinedTextFieldPasswordSignUp(label = "Password", password = password, onPasswordChange = { password = it })

            Spacer(modifier = Modifier.height(29.dp))

            OutlinedTextFieldConfirmPassword(label = "Confirm Password", password = password, confirmPassword = confirmPassword, onConfirmPasswordChange = { confirmPassword = it })

            Spacer(modifier = Modifier.height(29.dp))

            BasicButton(
                label = "Sign up",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(29.dp))

            NormalTextComponent(value = "Already have an account")
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
