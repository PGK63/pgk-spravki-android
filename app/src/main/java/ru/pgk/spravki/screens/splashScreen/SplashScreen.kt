package ru.pgk.spravki.screens.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.pgk.spravki.R
import ru.pgk.spravki.data.database.UserDataSource
import ru.pgk.spravki.ui.theme.primaryBackgroundColor
import ru.pgk.spravki.ui.theme.primaryTextColor
import ru.pgk.spravki.ui.theme.tintColor

@Composable
fun SplashScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val primaryTextColor = primaryTextColor()
    val primaryBackgroundColor = primaryBackgroundColor()
    val userDataSource = remember { UserDataSource(context) }

    LaunchedEffect(key1 = Unit, block = {
        delay(3000L)
        val userId = userDataSource.getUserId()

        if(userId == 0){
            navController.navigate("auth")
        }else{
            navController.navigate("main")
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(tintColor)
    ) {
        Card(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center),
            backgroundColor = primaryBackgroundColor,
            shape = AbsoluteRoundedCornerShape(180.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pgk_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(5.dp)
                )

                Text(
                    text = "отделение ит",
                    modifier = Modifier.padding(5.dp),
                    color = tintColor
                )
            }
        }
    }
}