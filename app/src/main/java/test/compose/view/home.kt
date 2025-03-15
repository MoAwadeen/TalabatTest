package test.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import test.compose.components.BottomAppBar
import test.compose.ui.theme.Bg
import test.compose.components.CardContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import test.compose.components.BoldTextComponent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun HomeScreen(navController: NavController) {
    var userName by remember { mutableStateOf("User") }
    var isBottomBarVisible by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (available.y > 0) {
                    isBottomBarVisible = true
                } else if (available.y < 0) {
                    isBottomBarVisible = false
                }
                return Offset.Zero
            }
        }
    }

    LaunchedEffect(Unit) {
        getUserFirstName { name ->
            userName = name
        }
    }

    Scaffold(
        topBar = {
            HomeAppToolbar("Card Carousel", userName)
        },
        bottomBar = {
            AnimatedVisibility(visible = isBottomBarVisible) {
                BottomAppBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding).nestedScroll(nestedScrollConnection),
            color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                items(5) {
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

                    Spacer(modifier = Modifier.height(20.dp))

                    BoldTextComponent("Home")
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
        HomeScreen(rememberNavController())
    }
}