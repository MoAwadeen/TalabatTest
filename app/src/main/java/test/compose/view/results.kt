package test.compose.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import test.compose.components.AppToolbar
import test.compose.components.BottomAppBar
import test.compose.components.HorizontalLine
import test.compose.components.ResultsCardContent
import test.compose.ui.theme.Bg
import test.compose.ui.theme.Orange

@Composable
fun ResultsScreen(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var userName by remember { mutableStateOf("User") }
    var selectedImage by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        fetchUserData { name, base64Image ->
            userName = name
            base64Image?.let {
                selectedImage = base64ToBitmap(it)?.asImageBitmap()
            }
        }
    }

    Scaffold(
        topBar = { AppToolbar(toolbarTitle = "Results", height = 90) },
        bottomBar = { BottomAppBar(navController, currentRoute) }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(start = 15.dp, end = 15.dp, top = 4.dp)
                .padding(innerPadding)
        ) {
            val pagerState = rememberPagerState(initialPage = 0) { texts2.size }

            VerticalPager(
                state = pagerState,
                pageSpacing = 32.dp,
                pageSize = androidx.compose.foundation.pager.PageSize.Fixed(140.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) { index ->
                ResultsCardContent(
                    index = index,
                    pagerState = pagerState,
                    selectedImage = selectedImage,
                    userName = userName,
                    text2 = texts2,
                    onClick = { navController.navigate(Routes.PROFILE) }
                )

                HorizontalLine(color = Orange, thickness = 5f)
            }
        }
    }
}

private fun base64ToBitmap(base64String: String): Bitmap? {
    return try {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        Log.e("ProfileScreen", "Error decoding base64 string", e)
        null
    }
}

private fun fetchUserData(onResult: (String, String?) -> Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

    FirebaseFirestore.getInstance().collection("users").document(userId)
        .get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val firstName = document.getString("firstName") ?: "User"
                val profileImage = document.getString("profileImage")
                onResult(firstName, profileImage)
            }
        }
        .addOnFailureListener { exception ->
            Log.e("ProfileScreen", "Error fetching user data", exception)
            onResult("User", null)
        }
}

@Preview
@Composable
fun ResultsScreenPreview(){
    Surface(modifier = Modifier.fillMaxSize().background(Bg)) {
        ResultsScreen(rememberNavController())
    }
}