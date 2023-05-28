package ru.pgk.spravki.screens.sendRequestScreen

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.pgk.spravki.data.api.model.department.DepartmentType
import ru.pgk.spravki.data.api.model.request.SendRequestBody
import ru.pgk.spravki.screens.mainScreen.MainViewModel
import ru.pgk.spravki.ui.theme.primaryBackgroundColor
import ru.pgk.spravki.ui.theme.primaryTextColor
import ru.pgk.spravki.ui.theme.secondaryBackgroundColor
import ru.pgk.spravki.ui.theme.tintColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SendRequestScreen(
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var departmentTypes by remember { mutableStateOf<List<DepartmentType>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit, block = {
        departmentTypes = viewModel.getDepartmentTypes()
    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = primaryBackgroundColor()
    ) {
        LazyColumn(

        ) {
            items(departmentTypes){ departmentType ->

                var count by remember { mutableStateOf("1") }
                var expandedState by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                    shape = AbsoluteRoundedCornerShape(15.dp),
                    backgroundColor = secondaryBackgroundColor(),
                    elevation = 4.dp,
                    onClick = {
                        expandedState = !expandedState
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        if(!expandedState){
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .weight(6f),
                                    text = departmentType.types.first().text,
                                    fontSize = MaterialTheme.typography.h6.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = primaryTextColor(),
                                    textAlign = TextAlign.Center
                                )

//                                IconButton(
//                                    modifier = Modifier
//                                        .weight(1f)
//                                        .alpha(ContentAlpha.medium)
//                                        .rotate(rotationState),
//                                    onClick = {
//                                        expandedState = !expandedState
//                                    }
//                                ) {
//                                    Icon(
//                                        imageVector = Icons.Default.ArrowDropDown,
//                                        contentDescription = "Drop-Down Arrow",
//                                        tint = primaryTextColor()
//                                    )
//                                }
                            }
                        }

                        if(expandedState){

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if(isLoading){
                                    CircularProgressIndicator(
                                        color = tintColor,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = "Тип:",
                                    color = primaryTextColor()
                                )

                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = departmentType.types.first().text,
                                    color = primaryTextColor(),
                                    textAlign = TextAlign.End
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.padding(5.dp),
                                    text = "Количество:",
                                    color = primaryTextColor()
                                )

                                OutlinedTextField(
                                    value = count,
                                    onValueChange = {count = it},
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .width(100.dp),
                                    shape = AbsoluteRoundedCornerShape(15.dp),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number
                                    ),
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        backgroundColor = secondaryBackgroundColor(),
                                        focusedLabelColor = tintColor,
                                        cursorColor = tintColor,
                                        unfocusedLabelColor = primaryBackgroundColor(),
                                        focusedBorderColor = tintColor,
                                        unfocusedBorderColor = primaryBackgroundColor(),
                                        textColor = primaryTextColor()
                                    ),
                                    trailingIcon = {
                                        Text(
                                            text = "шт",
                                            color = primaryTextColor()
                                        )
                                    }
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .widthIn(min = 100.dp)
                                        .heightIn(min = 50.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = tintColor),
                                    shape = AbsoluteRoundedCornerShape(25.dp),
                                    onClick = {
                                        expandedState = false
                                    }
                                ) {
                                    Text(text = "Отмена", color = Color.White)
                                }

                                Button(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .widthIn(min = 100.dp)
                                        .heightIn(min = 50.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = tintColor),
                                    shape = AbsoluteRoundedCornerShape(25.dp),
                                    onClick = {
                                        if(!isLoading){
                                            isLoading = true
                                            viewModel.sendRequest(
                                                body = SendRequestBody(
                                                    count = count.toIntOrNull() ?: 0,
                                                    departmentId = departmentType.department.id,
                                                    documentType = departmentType.types.first()
                                                ),
                                                onSuccess = {
                                                    Toast.makeText(context, "Заявка отправлена", Toast.LENGTH_SHORT)
                                                        .show()
                                                },
                                                onError = {
                                                    Toast.makeText(context, it, Toast.LENGTH_SHORT)
                                                        .show()
                                                },
                                                onFinally = {
                                                    isLoading = false
                                                }
                                            )
                                        }
                                    }
                                ) {
                                    Text(text = "Заказать", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}