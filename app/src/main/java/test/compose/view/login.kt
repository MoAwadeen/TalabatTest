package test.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import test.compose.AuthResponse
import test.compose.AuthenticationManager
import test.compose.components.BasicButton
import test.compose.components.BoldTextComponent
import test.compose.components.ClickableTextComponent
import test.compose.components.NormalTextComponent
import test.compose.components.OutlinedTextFieldEmail
import test.compose.components.OutlinedTextFieldPassword
import test.compose.components.ShowAlertDialog
import test.compose.ui.theme.Bg
import test.compose.ui.theme.Orange

@Composable
fun LoginScreen(navController: NavController){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val authManager = AuthenticationManager()
    val coroutineScope = rememberCoroutineScope()


    Surface (
        modifier = Modifier.fillMaxWidth().background(Color.White).padding(28.dp),
        color = Color.White

    )
    {
        Column (modifier = Modifier.fillMaxSize()){
            Spacer(modifier = Modifier.height(20.dp))
            BoldTextComponent(value = "Login here")
            Spacer(modifier = Modifier.height(26.dp))
            NormalTextComponent(value = "Welcome back you’ve\n" +
                    "been missed!")

            Spacer(modifier = Modifier.height(74.dp))

            OutlinedTextFieldEmail(
                label = "Email Address",
                text = email,
                onValueChange = { email = it }  // Ensure state updates
            )

            Spacer(modifier = Modifier.height(29.dp))
            OutlinedTextFieldPassword(
                label = "Password",
                text = password,
                onValueChange = { password = it }  // Ensure state updates
            )
            Spacer(modifier = Modifier.height(29.dp))
            BasicButton(
                label = "Sign in",
                onClick = {
                    coroutineScope.launch {
                        authManager.SignInWithEmailAndPassword(email, password).collect { response ->
                            when (response) {
                                is AuthResponse.Success -> navController.navigate(Routes.HOME)
                                is AuthResponse.Error -> errorMessage = response.message
                            }
                        }
                    }
                    if (email.isBlank() || password.isBlank()) {
                        errorMessage = "Email and password cannot be empty"
                        return@BasicButton
                    }
                }
            )
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                BoldTextComponent(value = errorMessage)
            }
            Spacer(modifier = Modifier.height(29.dp))
            ClickableTextComponent(
                value = "Create new account ?",
                onClick = {navController.navigate(Routes.REGISTER)}
            )

        }


    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    Surface (
        modifier = Modifier.fillMaxSize().background(Bg),
    ){
        LoginScreen(rememberNavController())
    }

}


