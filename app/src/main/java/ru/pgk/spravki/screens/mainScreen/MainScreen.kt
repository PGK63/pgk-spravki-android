package ru.pgk.spravki.screens.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.pgk.spravki.R
import ru.pgk.spravki.screens.sendRequestScreen.SendRequestScreen
import ru.pgk.spravki.screens.userScreen.UserScreen
import ru.pgk.spravki.ui.theme.primaryBackgroundColor
import ru.pgk.spravki.ui.theme.primaryTextColor
import ru.pgk.spravki.ui.theme.secondaryBackgroundColor
import ru.pgk.spravki.ui.theme.tintColor

private enum class BottomBarItem(val icon: Int) {
    BOOK(R.drawable.book),
    MESSAGE(R.drawable.message),
    USER(R.drawable.user),
    SETTINGS(R.drawable.settings)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val primaryTextColor = primaryTextColor()
    val primaryBackgroundColor = primaryBackgroundColor()

    var bottomBarItem by remember { mutableStateOf(BottomBarItem.USER) }
    var sendRequestScreen by remember { mutableStateOf(false) }

    Scaffold(
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { sendRequestScreen = true },
                backgroundColor = tintColor
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = primaryBackgroundColor,
                elevation = 6.dp
            ) {
                BottomBarItem.values().forEach {
                    BottomNavigationItem(
                        selected = bottomBarItem == it && !sendRequestScreen,
                        onClick = { bottomBarItem = it; sendRequestScreen = false },
                        icon = {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        unselectedContentColor = primaryTextColor,
                        selectedContentColor = tintColor
                    )
                }
            }
        }
    ) { p ->
        Surface(
            modifier = Modifier.padding(p)
        ) {
            if(sendRequestScreen){
                SendRequestScreen(viewModel = viewModel)
            }else {
                when(bottomBarItem){
                    BottomBarItem.BOOK -> Unit
                    BottomBarItem.MESSAGE -> Unit
                    BottomBarItem.USER -> UserScreen(navController = navController, viewModel = viewModel)
                    BottomBarItem.SETTINGS -> Unit
                }
            }
        }
    }
}