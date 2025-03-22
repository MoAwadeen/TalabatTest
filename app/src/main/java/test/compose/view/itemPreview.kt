package test.compose.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import test.compose.R
import test.compose.components.BasicButton
import test.compose.components.BottomAppBar
import test.compose.components.ItemToolbar
import test.compose.ui.theme.Bg
import test.compose.ui.theme.Brown
import test.compose.ui.theme.Orange


@Composable
fun ItemScreen(navController: NavController) {
    var isBottomBarVisible by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            ItemToolbar("Cheese Burger")
        },
        bottomBar = {
            AnimatedVisibility(visible = isBottomBarVisible) {
                BottomAppBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.burger), // Replace with actual image
                    contentDescription = "Classic Cheeseburger",
                    modifier = Modifier.fillMaxWidth()
                )

                // Box for Text and Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {

                        // Item Description
                        Text(
                            text = "A juicy and delicious cheeseburger with fresh lettuce, tomatoes, and melted cheese.",
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Left,
                            fontSize = 18.sp,
                            color = Brown,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Item Price
                        Text(
                            text = "EGP 190.00",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Orange,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.location),
                                contentDescription = "Location",
                                tint = Orange,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Shorouk City, Cairo",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Orange
                                )
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = "Rating",
                                tint = Orange,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "4.5 (1000+)",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Orange
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(96.dp))

                        BasicButton(label = "Add to Cart", onClick = {})

                        }
                    }
                }
            }
        }
    }

@Preview
@Composable
fun ItemScreenPreview(){
    Surface (
        modifier = Modifier.fillMaxSize().background(Bg),
    ){
        ItemScreen(rememberNavController())
    }
}