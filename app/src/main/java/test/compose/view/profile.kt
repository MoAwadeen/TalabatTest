package test.compose.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import test.compose.components.AppToolbar
import test.compose.components.BottomAppBar
import test.compose.components.DifferentSizeTextComponent
import test.compose.components.LogOutButton
import test.compose.components.ProfileImage
import test.compose.components.ThreeItemsRow
import test.compose.components.TwoItemsRow
import test.compose.components.UserResultsButton
import test.compose.ui.theme.Bg
import java.io.ByteArrayOutputStream

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var selectedImageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var userName by remember { mutableStateOf("User Name") }

    LaunchedEffect(Unit) {
        fetchUserData { name, base64Image ->
            userName = name
            base64Image?.let {
                selectedImageBitmap = base64ToBitmap(it)?.asImageBitmap()
            }
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val bitmap = uriToBitmap(uri, context)
                selectedImageBitmap = bitmap?.asImageBitmap()
                bitmap?.let { uploadProfileImageToFireStore(it) }
            }
        }
    )

    Scaffold(
        topBar = { AppToolbar(toolbarTitle = "", height = 40) },
        bottomBar = { BottomAppBar(navController, currentRoute) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    ProfileImage(
                        selectedImage = selectedImageBitmap,
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.fillMaxWidth().height(50.dp))

                    DifferentSizeTextComponent(value = userName, size = 24.sp)

                    LazyRow(modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp),
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        item {
                            UserResultsButton(label = "2 Wins")

                            UserResultsButton(label = "1 Loses")

                            UserResultsButton(label = "3404 Rank")

                            UserResultsButton(label = "15% Conf")
                        }
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))

                    ThreeItemsRow(icon = Icons.Filled.Redeem , label = "Rewards", text = "750 Points")

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    ThreeItemsRow(icon = Icons.Filled.Flag , label = "Country", text = "Egypt")

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    ThreeItemsRow(icon = Icons.Filled.GTranslate , label = "Language", text = "English")

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    TwoItemsRow(icon = Icons.AutoMirrored.Filled.VolumeUp, label = "Notifications")

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    TwoItemsRow(icon = Icons.Filled.Redeem , label = "Vouchers")

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    TwoItemsRow(icon = Icons.Filled.PersonAddAlt , label = "Invite Friends")

                    Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))

                    TwoItemsRow(icon = Icons.Filled.Info , label = "About App")

                    Spacer(modifier = Modifier.fillMaxWidth().height(30.dp))

                    LogOutButton(onClick = {navController.navigate(Routes.SPLASH)})
                }
            }
        }
    }
}

private fun uriToBitmap(uri: Uri, context: android.content.Context): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    } catch (e: Exception) {
        Log.e("ProfileScreen", "Error converting URI to bitmap", e)
        null
    }
}

private fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
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

private fun uploadProfileImageToFireStore(bitmap: Bitmap) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val base64Image = bitmapToBase64(bitmap)
    val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)
    val userData = hashMapOf("profileImage" to base64Image)

    userRef.set(userData, SetOptions.merge())
        .addOnSuccessListener {
            Log.d("ProfileScreen", "Profile image saved to FireStore")
        }
        .addOnFailureListener { exception ->
            Log.e("ProfileScreen", "Error saving profile image to FireStore", exception)
        }
}

private fun fetchUserData(onResult: (String, String?) -> Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val defaultImage = Icons.Filled.AccountCircle

    FirebaseFirestore.getInstance().collection("users").document(userId)
        .get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val firstName = document.getString("firstName") ?: "User"
                val lastName = document.getString("lastName") ?: "Name"
                val profileImage = document.getString("profileImage")
                onResult("$firstName $lastName", profileImage)
            }
        }
        .addOnFailureListener { exception ->
            Log.e("ProfileScreen", "Error fetching user data", exception)
            onResult("User Name", defaultImage.toString())
        }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    Surface(modifier = Modifier.fillMaxSize().background(Bg)) {
        ProfileScreen(rememberNavController())
    }
}