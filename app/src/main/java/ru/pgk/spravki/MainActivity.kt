package ru.pgk.spravki

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.pgk.spravki.screens.authScreen.AuthScreen
import ru.pgk.spravki.screens.mainScreen.MainScreen
import ru.pgk.spravki.screens.splashScreen.SplashScreen
import ru.pgk.spravki.ui.theme.MainTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "splash"
            ){
                composable("splash"){
                    SplashScreen(navController)
                }

                composable("auth"){
                    AuthScreen(navController)
                }

                composable("main"){
                    MainScreen(navController)
                }
            }
        }
    }
}
