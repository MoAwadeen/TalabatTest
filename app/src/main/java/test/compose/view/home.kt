package test.compose.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import test.compose.components.BottomAppBar
import test.compose.components.FavoriteCardContext
import test.compose.components.HomeAppToolbar
import test.compose.components.ImageCardContext
import test.compose.components.ImageTextCardContext
import test.compose.components.ImageTwoTextCardContext
import test.compose.components.NewsBox
import test.compose.components.TwoTextsRow
import test.compose.ui.theme.Bg

@Composable
fun HomeScreen(navController: NavController) {
    var userName by remember { mutableStateOf("User") }
    var isBottomBarVisible by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var favorites by remember { mutableStateOf(setOf<Int>()) }

    val actions = listOf(
        { navController.navigate(Routes.PROFILE) },
        { navController.navigate(Routes.SPLASH) },
        { navController.navigate(Routes.PROFILE) },
        { navController.navigate(Routes.SPLASH) },
        { navController.navigate(Routes.PROFILE) },
        { navController.navigate(Routes.SPLASH) },
        { navController.navigate(Routes.PROFILE) },
        { navController.navigate(Routes.SPLASH) }
    )

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
            modifier = Modifier.fillMaxSize().padding(innerPadding).nestedScroll(nestedScrollConnection)
                .background(Color.White),
            color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp).background(Color.White),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    val pagerState1 = rememberPagerState(initialPage = 0) {
                        images.size
                    }
                    val pagerState2 = rememberPagerState(initialPage = 0) {
                        images.size
                    }
                    val pagerState3 = rememberPagerState(initialPage = 0) {
                        images.size
                    }
                    val pagerStateFavorites = rememberPagerState(initialPage = 0) {
                        images.size
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalPager(
                        state = pagerState1,
                        pageSize = androidx.compose.foundation.pager.PageSize.Fixed(140.dp),
                        pageSpacing = 8.dp,
                        modifier = Modifier.fillMaxWidth().height(140.dp).background(Color.White)
                    ) { index ->
                        ImageTextCardContext(index, pagerState1, images, texts1, actions)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    NewsBox(imageUrl = "https://www.w3schools.com/w3images/forest.jpg")

                    Spacer(modifier = Modifier.height(20.dp))

                    TwoTextsRow(
                        text1 = "Images",
                        text2 = "View All",
                        onClick = { navController.navigate(Routes.PROFILE) }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalPager(
                        state = pagerState2,
                        pageSize = androidx.compose.foundation.pager.PageSize.Fixed(140.dp),
                        pageSpacing = 8.dp,
                        modifier = Modifier.fillMaxWidth().height(160.dp)
                    ) { index ->
                        ImageCardContext(index, pagerState2, images, actions)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TwoTextsRow(
                        text1 = "Images With Description",
                        text2 = "View All",
                        onClick = { navController.navigate(Routes.PROFILE) }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalPager(
                        state = pagerState3,
                        pageSize = androidx.compose.foundation.pager.PageSize.Fixed(140.dp),
                        pageSpacing = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) { index ->
                        ImageTwoTextCardContext(index, pagerState3, images, texts1, texts2, actions)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TwoTextsRow(text1 = "Images With Favorite", text2 = "View All", onClick = { navController.navigate(Routes.PROFILE) }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    HorizontalPager(
                        state = pagerStateFavorites,
                        pageSize = androidx.compose.foundation.pager.PageSize.Fixed(140.dp),
                        pageSpacing = 8.dp,
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    ) { index ->
                        FavoriteCardContext(
                            index = index,
                            pagerState = pagerStateFavorites,
                            images = images,
                            text1 = texts1,
                            text2 = texts2,
                            onClick = actions,
                            isFavorite = favorites.contains(index),
                            onFavoriteClick = { isFavorite ->
                                favorites = if (isFavorite) {
                                    favorites + index
                                } else {
                                    favorites - index
                                }
                            }
                        )
                    }
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

val texts1 = listOf(
    "Lights",
    "Mountains",
    "Forest",
    "Nature",
    "Lights",
    "Mountains",
    "Forest",
    "Nature"
)

val texts2 = listOf(
    "1",
    "2",
    "3",
    "4",
    "1",
    "2",
    "3",
    "4"
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