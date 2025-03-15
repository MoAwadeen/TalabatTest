package test.compose.view

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import test.compose.components.AppToolbar
import test.compose.components.DifferentSizeTextComponent
import test.compose.components.BottomAppBar
import test.compose.components.ProfileImage
import test.compose.ui.theme.Bg

@Composable
fun ProfileScreen(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var userName by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        fetchUserName { name ->
            userName = name
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
                uploadProfileImageToFirebase(uri)
            }
        }
    )

    Scaffold(
        topBar = {
            AppToolbar("")
        },
        bottomBar = {
            BottomAppBar(navController, currentRoute)
        }
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
                        selectedImageUri = selectedImageUri,
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    DifferentSizeTextComponent(value = userName, size = 20.sp)
                }
            }
        }
    }
}

private fun fetchUserName(onResult: (String) -> Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    FirebaseFirestore.getInstance().collection("users").document(userId)
        .get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val firstName = document.getString("firstName") ?: "User"
                val lastName = document.getString("lastName") ?: "Name"
                onResult("$firstName $lastName")
            }
        }
        .addOnFailureListener { exception ->
            onResult("User Name")
            Log.e("ProfileScreen", "Error fetching user name", exception)
        }
}

private fun uploadProfileImageToFirebase(uri: Uri) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")

    storageRef.putFile(uri)
        .addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                saveProfileImageUrlToFireStore(downloadUrl.toString())
            }
        }
        .addOnFailureListener { exception ->
            Log.e("ProfileScreen", "Error uploading image", exception)
        }
}

private fun saveProfileImageUrlToFireStore(imageUrl: String) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val userRef = FirebaseFirestore.getInstance().collection("users").document(userId)

    userRef.update("profileImageUrl", imageUrl)
        .addOnSuccessListener {
            Log.d("ProfileScreen", "Profile image URL saved to FireStore")
        }
        .addOnFailureListener { exception ->
            Log.e("ProfileScreen", "Error saving image URL to FireStore", exception)
        }
}

@Preview
@Composable
fun ProfileScreenPreview(){
    Surface (
        modifier = Modifier.fillMaxSize().background(Bg),
    ){
        ProfileScreen(rememberNavController())
    }
}