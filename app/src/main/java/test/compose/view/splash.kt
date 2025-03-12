package test.compose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import test.compose.R
import test.compose.components.BasicButton
import test.compose.ui.theme.Bg

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Bg)
            .padding(28.dp),
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

            BasicButton(
                label = "Login",
                onClick = { navController.navigate(Routes.LOGIN) }
            )
            Spacer(modifier = Modifier.height(16.dp))

            BasicButton(
                label = "Sign Up",
                onClick = { navController.navigate(Routes.REGISTER) }
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize().background(Bg),
    ) {
        SplashScreen(rememberNavController())
    }
}
