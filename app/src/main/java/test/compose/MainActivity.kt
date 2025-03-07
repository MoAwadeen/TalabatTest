package test.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import test.compose.ui.theme.MyApplicationTheme
import test.compose.view.AppNavigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                NavigatePage()
            }
        }
    }
}

@Composable
fun NavigatePage() {
    AppNavigationGraph()
}



