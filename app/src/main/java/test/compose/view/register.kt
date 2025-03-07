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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import test.compose.components.BasicButton
import test.compose.components.BoldTextComponent
import test.compose.components.ClickableTextComponent
import test.compose.components.DifferentSizeTextComponent
import test.compose.components.OutlinedTextFieldConfirmPassword
import test.compose.components.OutlinedTextFieldEmail
import test.compose.components.OutlinedTextFieldName
import test.compose.components.OutlinedTextFieldPasswordSignUp
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
            Spacer(modifier = Modifier.height(20.dp))

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
                onClick = {navController.navigate(Routes.LOGIN)}
            )

            Spacer(modifier = Modifier.height(29.dp))

            ClickableTextComponent(
                value = "Already have an account",
                onClick ={navController.navigate(Routes.LOGIN)}
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
