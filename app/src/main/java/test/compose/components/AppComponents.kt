package test.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import test.compose.ui.theme.*

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
fun OutlinedTextFieldEmail(label: String) {
    val text = remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        placeholder = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth().background(Bg),
        isError = text.value.isNotEmpty() && !text.value.contains('@'),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Orange,   // Blue when focused
            unfocusedBorderColor = Bg, // Gray when not focused
            cursorColor = Orange,
            focusedLabelColor = Bg,
            unfocusedLabelColor = Brown  )// Label color when not focused
    )
}

@Composable
fun OutlinedTextFieldPassword(label: String) {
    val text = remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        placeholder = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth().background(Bg),
        isError = text.value.isNotEmpty() && !text.value.contains('@'),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Orange,   // Blue when focused
            unfocusedBorderColor = Bg, // Gray when not focused
            cursorColor = Orange,
            focusedLabelColor = Bg,
            unfocusedLabelColor = Brown  )// Label color when not focused
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
