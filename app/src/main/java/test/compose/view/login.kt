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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import test.compose.components.BasicButton
import test.compose.components.BoldTextComponent
import test.compose.components.ClickableTextComponent
import test.compose.components.NormalTextComponent
import test.compose.components.OutlinedTextFieldEmail
import test.compose.components.OutlinedTextFieldPassword
import test.compose.ui.theme.Bg

@Composable
fun LoginScreen(navController: NavController){
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

            OutlinedTextFieldEmail(label = "Email Address")
            Spacer(modifier = Modifier.height(29.dp))
            OutlinedTextFieldPassword(label = "Password")
            Spacer(modifier = Modifier.height(29.dp))
            BasicButton(
                label = "Sign in",
                onClick = {navController.navigate(Routes.REGISTER)}
            )
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


