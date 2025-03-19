package test.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import test.compose.R
import test.compose.ui.theme.Bg
import test.compose.ui.theme.Brown
import test.compose.ui.theme.Orange
import test.compose.view.Routes
import kotlin.math.absoluteValue

@Composable
fun BoldTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 80.dp),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            color = Orange),
        textAlign = TextAlign.Center
    )
}

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            color = Brown),
        textAlign = TextAlign.Center
    )
}

@Composable
fun OutlinedTextFieldEmail(label: String, text: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,  // Add this to update state
        placeholder = { Text(label, color = Orange) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .background(Bg),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Orange,
            unfocusedBorderColor = Bg,
            cursorColor = Orange
        )
    )
}

@Composable
fun OutlinedTextFieldPassword(label: String, text: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,  // Add this to update state
        placeholder = { Text(label, color = Orange) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().background(Bg),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Orange,
            unfocusedBorderColor = Bg,
            cursorColor = Orange
        )
    )
}

@Composable
fun BasicButton(label: String, onClick: () -> Unit ){
    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Orange),
        modifier = Modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(100.dp))
    {
        Text(label, fontSize = 20.sp, color = Color.White , fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun DifferentSizeTextComponent(value: String, size: TextUnit) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = size,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            color = Brown),
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun DifferentSizeTextComponentPreview(){
    DifferentSizeTextComponent(value = "Hello" , size = 20.sp)
}

@Composable
fun OutlinedTextFieldName(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Orange) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .background(Bg),
        isError = value.isNotEmpty() && value.length < 2,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Orange,
            unfocusedBorderColor = Bg,
            cursorColor = Orange
        )
    )
}

@Preview
@Composable
fun OutlinedTextFieldNamePreview(){
    //OutlinedTextFieldName(label = "Merna")
}

@Composable
fun OutlinedTextFieldPasswordSignUp(label: String, password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text(label, color = Orange) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().background(Bg),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Orange,
            unfocusedBorderColor = Bg,
            cursorColor = Orange,
            focusedLabelColor = Bg,
            unfocusedLabelColor = Brown)
    )
}

@Composable
fun OutlinedTextFieldConfirmPassword(label: String, password: String, confirmPassword: String, onConfirmPasswordChange: (String) -> Unit) {
    val localFocusManager = LocalFocusManager.current
    val isError = confirmPassword.isNotEmpty() && confirmPassword != password

    OutlinedTextField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        placeholder = { Text(label, color = Orange) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().background(Bg),
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) Color.Red else Orange,
            unfocusedBorderColor = if (isError) Color.Red else Bg,
            cursorColor = Orange,
            focusedLabelColor = Bg,
            unfocusedLabelColor = Brown
        )
    )

    if (isError) {
        Text(
            text = "Passwords do not match",
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Preview
@Composable
fun OutlinedTextFieldConfirmPasswordPreview() {
    val password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    OutlinedTextFieldConfirmPassword(label = "Confirm Password", password = password, confirmPassword = confirmPassword, onConfirmPasswordChange = { confirmPassword = it })
}

@Composable
fun ClickableTextComponent(value: String , onClick: () -> Unit) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth().heightIn(min = 40.dp).clickable { onClick() },
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            color = Brown),
        textAlign = TextAlign.Center
    )
}

@Composable
fun ShowAlertDialog() {
    var showDialog by remember { mutableStateOf(false) }

    Button(onClick = { showDialog = true }) {
        Text("Show Alert")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Alert") },
            text = { Text("This is an alert message.") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ImageCardContext(index: Int, pagerState: PagerState, images: List<String>, onClick: List<() -> Unit>) {
    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(140.dp)
            .height(150.dp)
            .clickable {
                onClick[index]()
            }
            .graphicsLayer {
                alpha = lerp(
                    start = 0.8f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f))
            }) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(images[index])
                .crossfade(true)
                .scale(Scale.FILL)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun GoogleSignInButton(onClick: () -> Unit) {

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Bg),
        onClick = onClick, // Orange color
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(100.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),

        ) {
            Image(
                painter = painterResource(id = R.drawable.google), // Add Google logo in drawable
                contentDescription = "Google Sign-In",
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "Sign in with Google",
                color = Brown,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun GoogleSignInButtonPreview() {
    GoogleSignInButton(onClick = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(toolbarTitle: String) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth().height(40.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Orange,
            titleContentColor = Color.White,
        ),
        title = {
            Text(
                text = toolbarTitle,
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppToolbar(toolbarTitle: String, userName: String) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth().height(90.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Orange,
            titleContentColor = Color.White,
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = toolbarTitle,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )
                Text(
                    text = "Hi, $userName 👋",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    )
}

@Composable
fun BottomAppBar(navController: NavController, currentRoute: String?) {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        containerColor = Orange,
        contentColor = Color.White,
        tonalElevation = 8.dp,
        actions = {
            Row(
                modifier = Modifier.fillMaxSize().align(Alignment.Top).padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(modifier = Modifier.size(24.dp), onClick = { navController.navigate(Routes.HOME) }) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    if (currentRoute == Routes.HOME) {
                        Text(
                            text = "Home",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(modifier = Modifier.size(24.dp), onClick = { navController.navigate(Routes.SPLASH) }) {
                        Icon(
                            Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorites",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    if (currentRoute == Routes.SPLASH) {
                        Text(
                            text = "Favorites",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(modifier = Modifier.size(24.dp), onClick = { navController.navigate(Routes.PROFILE) }) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Account",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    if (currentRoute == Routes.PROFILE) {
                        Text(
                            text = "Account",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun BottomAppBarPreview() {
    val navController = rememberNavController()
    val currentRoute = Routes.HOME
    BottomAppBar(navController = navController, currentRoute = currentRoute)
}

@Composable
fun ProfileImage(selectedImage: ImageBitmap?, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Orange).height(100.dp).offset(y = 40.dp)
        , contentAlignment = Alignment.BottomCenter
    ) {
        if (selectedImage != null) {
            Image(
                bitmap = selectedImage,
                contentDescription = "Profile Image",
                modifier = Modifier.size(80.dp).clip(CircleShape).clickable { onClick() }
                    .border(4.dp, Orange,RoundedCornerShape(100.dp))
                , contentScale = ContentScale.Crop)
        } else {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Default Profile Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(4.dp, Orange,RoundedCornerShape(100.dp))
                    .clickable { onClick() })
        } }
}

@Composable
fun LogOutButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().height(60.dp).border(4.dp, Orange, RoundedCornerShape(100.dp)),
            shape = RoundedCornerShape(100.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Log Out",
                    modifier = Modifier.size(20.dp),
                    tint = Orange
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "Log Out",
                    fontSize = 20.sp,
                    color = Orange,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun TwoItemsRow(icon: ImageVector, label: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Icon(
            icon,
            contentDescription = "Icon Description",
            modifier = Modifier.size(20.dp),
            tint = Orange
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = label,
            fontSize = 18.sp,
            color = Orange,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun ThreeItemsRow(icon: ImageVector, label: String, text: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Icon(
            icon,
            contentDescription = "Icon Description",
            modifier = Modifier.size(20.dp),
            tint = Orange
        )

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = label,
            fontSize = 20.sp,
            color = Orange,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = text,
            fontSize = 16.sp,
            color = Orange,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun UserResultsButton(label: String) {
    Row {
        Button(onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Orange),
            modifier = Modifier.fillMaxWidth().height(40.dp),
            shape = RoundedCornerShape(100.dp))
        {
            Text(label, fontSize = 14.sp, color = Color.White , fontWeight = FontWeight.Normal)
        }

        Spacer(modifier = Modifier.width(2.dp))
    }
}

@Composable
fun NewsBox(imageUrl: String) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Orange)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "The Spotlight !!",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            val painter: Painter = rememberAsyncImagePainter(model = imageUrl)

            Image(
                painter = painter,
                contentDescription = "News Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(16.dp))
            )
        }
    }
}

@Composable
fun ImageTextCardContext(index: Int,
                         pagerState: PagerState,
                         images: List<String>,
                         text: List<String>,
                         onClick: List<() -> Unit>) {
    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(140.dp)
            .height(160.dp)
            .background(Color.White)
            .clickable {
                onClick[index]()
            }
            .graphicsLayer {
                alpha = lerp(
                    start = 0.8f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                )
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp).background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[index])
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )

            text[index].split(",").forEach { item ->
                Text(
                    text = item.trim(),
                    fontSize = 16.sp,
                    color = Orange,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun TwoTextsRow(text1: String, text2: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Text(
            text = text1,
            fontSize = 20.sp,
            color = Orange,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = text2,
            fontSize = 18.sp,
            color = Color.Yellow,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Composable
fun ImageTwoTextCardContext(index: Int,
                            pagerState: PagerState,
                            images: List<String>,
                            text1: List<String>,
                            text2: List<String>,
                            onClick: List<() -> Unit>) {
    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(140.dp)
            .height(200.dp)
            .background(Color.White)
            .clickable {
                onClick[index]()
            }
            .graphicsLayer {
                alpha = lerp(
                    start = 0.8f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                )
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp).background(Color.White)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[index])
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )

            text1[index].split(",").forEach { item ->
                Text(
                    text = item.trim(),
                    fontSize = 16.sp,
                    color = Orange,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }

            text2[index].split(",").forEach { item ->
                Text(
                    text = item.trim(),
                    fontSize = 16.sp,
                    color = Orange,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun FavoriteCardContext(
    index: Int,
    pagerState: PagerState,
    images: List<String>,
    text1: List<String>,
    text2: List<String>,
    onClick: List<() -> Unit>,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit
) {
    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(140.dp)
            .height(200.dp)
            .background(Color.White)
            .clickable {
                onClick[index]()
            }
            .graphicsLayer {
                alpha = lerp(
                    start = 0.8f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                )
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(Color.White)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[index])
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = "Image",
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = { onFavoriteClick(!isFavorite) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(24.dp),
                    tint = if (isFavorite) Color.Red else Color.Red
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 130.dp)
            ) {
                text1[index].split(",").forEach { item ->
                    Text(
                        text = item.trim(),
                        fontSize = 16.sp,
                        color = Orange,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }

                text2[index].split(",").forEach { item ->
                    Text(
                        text = item.trim(),
                        fontSize = 16.sp,
                        color = Orange,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
