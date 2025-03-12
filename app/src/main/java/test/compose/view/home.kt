package test.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import test.compose.components.HomeAppToolbar
import test.compose.ui.theme.Bg
import test.compose.components.CardContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(navController: NavController) {
    var userName by remember { mutableStateOf("User") }

    LaunchedEffect(Unit) {
        getUserFirstName { name ->
            userName = name
        }
    }

    Scaffold(
        topBar = {
            HomeAppToolbar("Card Carousel", userName)
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            color = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                val pagerState = rememberPagerState(initialPage = 0) {
                    images.size
                }
                HorizontalPager(
                    state = pagerState,
                    pageSize = androidx.compose.foundation.pager.PageSize.Fixed(140.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    pageSpacing = 8.dp,
                    modifier = Modifier.fillMaxWidth().height(160.dp)
                ) { index ->
                    CardContext(index, pagerState, images, navController)
                }
            }
        }
    }
}

val images = listOf(
    "https://www.w3schools.com/w3images/lights.jpg",
    "https://www.w3schools.com/w3images/mountains.jpg",
    "https://www.w3schools.com/w3images/forest.jpg",
    "https://www.w3schools.com/w3images/nature.jpg",
    "https://www.w3schools.com/w3images/lights.jpg",
    "https://www.w3schools.com/w3images/mountains.jpg",
    "https://www.w3schools.com/w3images/forest.jpg",
    "https://www.w3schools.com/w3images/nature.jpg"
)

fun getUserFirstName(onResult: (String) -> Unit) {
    val user = FirebaseAuth.getInstance().currentUser
    user?.uid?.let { uid ->
        FirebaseFirestore.getInstance().collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                val firstName = document.getString("firstName") ?: "User"
                onResult(firstName)
            }
            .addOnFailureListener {
                onResult("User")
            }
    } ?: onResult("User")
}

@Preview
@Composable
fun HomeScreenPreview(){
    Surface (
        modifier = Modifier.fillMaxSize().background(Bg),
    ){
        LoginScreen(rememberNavController())
    }

}