package test.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import test.compose.ui.theme.Bg
import test.compose.ui.theme.Brown
import test.compose.ui.theme.Orange
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
        placeholder = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Blue
        )
    )
}

@Composable
fun OutlinedTextFieldPassword(label: String, text: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,  // Add this to update state
        placeholder = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Blue
        )
    )
}

@Composable
fun BasicButton(label: String, onClick: () -> Unit, ){

    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Orange),
        modifier = Modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(100.dp))
    {
        Text(label, fontSize = 20.sp, color = Color.White , fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)


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
        label = { Text(label) },
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
        placeholder = { Text(label) },
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
        placeholder = { Text(label) },
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
fun CardContext(index: Int, pagerState: PagerState, images: List<String>){

    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(2.dp).
            graphicsLayer {
            lerp(
                start = 0.9f.dp,
                stop = 1f.dp,
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f,1f)
            ).also{ scale ->
                scaleX = scale.value
                scaleY = scale.value
            }
                alpha = lerp(
                    start = 0.65f.dp,
                    stop = 1f.dp,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f,1f)
                ).value
        }

    ) {
        AsyncImage(
            modifier = Modifier.width(100.dp).height(100.dp),
            model = ImageRequest.Builder(LocalContext.current).
            data(images[index]).
            crossfade(true).
            scale(Scale.FILL).
            build(),
            contentDescription = "Image",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun CardContextPreview(){
    //OutlinedTextFieldName(label = "Merna")
}

