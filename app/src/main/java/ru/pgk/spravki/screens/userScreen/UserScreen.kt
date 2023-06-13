package ru.pgk.spravki.screens.userScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.pgk.spravki.data.api.model.user.User
import ru.pgk.spravki.ui.theme.primaryBackgroundColor
import ru.pgk.spravki.ui.theme.tintColor
import ru.pgk.spravki.R
import ru.pgk.spravki.data.api.model.request.Request
import ru.pgk.spravki.data.api.model.request.RequestStatus
import ru.pgk.spravki.screens.mainScreen.MainViewModel
import ru.pgk.spravki.ui.theme.primaryTextColor
import ru.pgk.spravki.ui.theme.secondaryBackgroundColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current

    val primaryTextColor = primaryTextColor()
    val primaryBackgroundColor = primaryBackgroundColor()

    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorText by remember { mutableStateOf("") }
    var requests by remember { mutableStateOf<List<Request>>(emptyList()) }

    LaunchedEffect(key1 = Unit, block = {
        try {
            isLoading = true
            val response = viewModel.getUserInfo()
            requests = viewModel.getRequests()
            if(response.isSuccessful){
                val body = response.body()!!
                if(body.error != null){
                    errorText = body.error
                }else{
                    user = body
                }
            }else {
                errorText = "Произошла ошибка"
            }
        }catch (e:Exception){
            errorText = "Произошла ошибка"
        }finally {
            isLoading = false
        }
    })

    BackdropScaffold(
        appBar = { /*TODO*/ },
        backLayerBackgroundColor = tintColor,
        gesturesEnabled = false,
        frontLayerBackgroundColor = primaryBackgroundColor,
        frontLayerShape = AbsoluteRoundedCornerShape(30.dp),
        peekHeight = 180.dp,
        backLayerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(5.dp)
                )

                Text(
                    text = user?.fatherName ?: "",
                    color = Color.White,
                    modifier = Modifier.padding(5.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        },
        frontLayerContent = {
            LazyColumn {

                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }

                if(isLoading){
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(color = tintColor)
                        }
                    }
                }else {
                    items(requests) { request ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            shape = AbsoluteRoundedCornerShape(15.dp),
                            backgroundColor = secondaryBackgroundColor(),
                            elevation = 4.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = request.documentType.type.text,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    color = primaryTextColor,
                                    fontSize = 18.sp
                                )

                                Spacer(modifier = Modifier.height(25.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = "Статус:",
                                        modifier = Modifier.padding(5.dp),
                                        color = primaryTextColor,
                                    )

                                    Text(
                                        text = request.status.text,
                                        modifier = Modifier.padding(5.dp),
                                        color = primaryTextColor,
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = "Количество:",
                                        modifier = Modifier.padding(5.dp),
                                        color = primaryTextColor,
                                    )

                                    Text(
                                        text = request.count.toString(),
                                        modifier = Modifier.padding(5.dp),
                                        color = primaryTextColor,
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = "Дата:",
                                        modifier = Modifier.padding(5.dp),
                                        color = primaryTextColor,
                                    )

                                    Text(
                                        text = request.getDate(),
                                        modifier = Modifier.padding(5.dp),
                                        color = primaryTextColor,
                                    )
                                }

                                Spacer(modifier = Modifier.height(25.dp))

                                if(request.status == RequestStatus.SEND){
                                    Button(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .widthIn(min = 250.dp)
                                            .heightIn(min = 50.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = tintColor),
                                        shape = AbsoluteRoundedCornerShape(25.dp),
                                        onClick = {
                                            isLoading = true

                                            viewModel.deleteRequest(
                                                requestId = request.id,
                                                onSuccess = {
                                                    requests = viewModel.getRequests()
                                                },
                                                onError = {
                                                    Toast.makeText(context, errorText, Toast.LENGTH_SHORT)
                                                        .show()
                                                },
                                                onFinally = {
                                                    isLoading = false
                                                }
                                            )
                                        }
                                    ) {
                                        Text(text = "Отменить заявку", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}