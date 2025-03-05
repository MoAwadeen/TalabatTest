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
import test.compose.R

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                painter = painterResource(id = R.drawable.talabat),
                contentDescription = "splash screen logo",
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { navController.navigate("login") }, modifier = Modifier.width(200.dp),colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff5a01))) {
                Text(text = "Login", fontSize = 28.sp,color = Color.White, fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif , fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate("signup") }, modifier = Modifier.width(200.dp),colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFff5a01))) {
                Text(text = "Sign Up", fontSize = 28.sp,color = Color.White, fontFamily = androidx.compose.ui.text.font.FontFamily.SansSerif , fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
        }
    }
}

