package test.compose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import test.compose.R
import test.compose.ui.theme.Bg
import test.compose.ui.theme.Brown
import test.compose.ui.theme.Orange

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Bg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .width(400.dp)
                    .height(400.dp),
                alignment = Alignment.TopCenter,
                painter = painterResource(id = R.drawable.splashimage),
                contentDescription = "splash screen logo",
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(34.dp))

            CustomButton(label = "Login") { navController.navigate("login") }
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(label = "Sign Up") { navController.navigate("signup") }
        }
    }
}

@Composable
fun CustomButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.width(220.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Orange)
    ) {
        Text(
            text = label,
            fontSize = 24.sp,
            color = Bg,
            fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}
