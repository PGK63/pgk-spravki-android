package ru.pgk.spravki.screens.authScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.pgk.spravki.R
import ru.pgk.spravki.ui.theme.primaryBackgroundColor
import ru.pgk.spravki.ui.theme.primaryTextColor
import ru.pgk.spravki.ui.theme.secondaryBackgroundColor
import ru.pgk.spravki.ui.theme.tintColor

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val primaryTextColor = primaryTextColor()
    val primaryBackgroundColor = primaryBackgroundColor()
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackgroundColor
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.pgk_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                        .padding(10.dp)
                )

                if(isLoading){
                    CircularProgressIndicator(color = tintColor)
                }

                Text(
                    text = "отделение ит",
                    modifier = Modifier.padding(5.dp),
                    color = tintColor
                )

                Text(
                    text = "Пожалуйста, войдите\nв свой аккаунт.",
                    modifier = Modifier.padding(5.dp),
                    textAlign = TextAlign.Center,
                    color = primaryTextColor
                )

                if(errorMessage.isNotEmpty()){
                    Text(
                        text = errorMessage,
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Red,
                        fontWeight = FontWeight.W900
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }else {
                    Spacer(modifier = Modifier.height(50.dp))
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it},
                    modifier = Modifier.padding(5.dp),
                    shape = AbsoluteRoundedCornerShape(15.dp),
                    label = {
                        Text(text = "Введите имя", color = primaryTextColor)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = secondaryBackgroundColor(),
                        focusedLabelColor = tintColor,
                        cursorColor = tintColor,
                        unfocusedLabelColor = primaryBackgroundColor,
                        focusedBorderColor = tintColor,
                        unfocusedBorderColor = primaryBackgroundColor,
                        textColor = primaryTextColor()
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    modifier = Modifier.padding(5.dp),
                    shape = AbsoluteRoundedCornerShape(15.dp),
                    label = {
                        Text(text = "Введите пароль", color = primaryTextColor)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = secondaryBackgroundColor(),
                        focusedLabelColor = tintColor,
                        cursorColor = tintColor,
                        unfocusedLabelColor = primaryBackgroundColor,
                        focusedBorderColor = tintColor,
                        unfocusedBorderColor = primaryBackgroundColor,
                        textColor = primaryTextColor()
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Забыли пароль",
                            modifier = Modifier.padding(2.dp),
                            color = primaryTextColor
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .padding(5.dp)
                        .widthIn(min = 340.dp)
                        .heightIn(min = 50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = tintColor),
                    shape = AbsoluteRoundedCornerShape(25.dp),
                    onClick = {
                        isLoading = true
                        errorMessage = ""

                        viewModel.login(
                            name = name.trim(),
                            password = password.trim(),
                            onSuccess = { navController.navigate("main") },
                            onError = { errorMessage = it },
                            onFinally = { isLoading = false }
                        )
                    }
                ) {
                    Text(text = "Вход", color = Color.White)
                }
            }
        }
    }
}