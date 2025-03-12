package test.compose.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import test.compose.ui.theme.Bg
import test.compose.components.BoldTextComponent
import test.compose.components.CardContext

@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            BoldTextComponent("Card Carousel")

            val pagerState = rememberPagerState(initialPage = 0) {
                images.size
            }

            HorizontalPager(
                state = pagerState,
                pageSize = androidx.compose.foundation.pager.PageSize.Fixed(140.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
                pageSpacing = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) { index ->
                CardContext(index, pagerState, images, navController)
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

@Preview
@Composable
fun HomeScreenPreview(){
    Surface (
        modifier = Modifier.fillMaxSize().background(Bg),
    ){
        LoginScreen(rememberNavController())
    }

}