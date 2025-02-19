package com.pasichdev.pharmate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pasichdev.pharmate.R
import com.pasichdev.pharmate.presentation.components.BottomNavigationBar
import com.pasichdev.pharmate.presentation.screens.AddItemPlaningScreen
import com.pasichdev.pharmate.presentation.screens.MenuScreen
import com.pasichdev.pharmate.presentation.screens.MondayScreen
import com.pasichdev.pharmate.presentation.screens.PlaningScreen
import com.pasichdev.pharmate.presentation.screens.TrackingScreen
import com.pasichdev.pharmate.ui.theme.PharmateTheme
import dagger.hilt.android.AndroidEntryPoint


sealed class NavigationItem(var route: String, var icon: Int, var title: Int) {
    object Monday : NavigationItem(Routes.Monday.toString(), R.drawable.ic_branch, R.string.monday)
    object Planning :
        NavigationItem(Routes.Planing.toString(), R.drawable.ic_calendar, R.string.planning)

    object Tracking :
        NavigationItem(Routes.Tracking.toString(), R.drawable.ic_heart, R.string.tracking)

    object Menu : NavigationItem(Routes.Menu.toString(), R.drawable.ic_menu, R.string.menu)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PharmateTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    val hideBottomBarRoutes = listOf(Routes.AddItemPlaning.toString())
    val shouldShowBottomBar by remember {
        derivedStateOf { backStackEntry?.destination?.route !in hideBottomBarRoutes }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = shouldShowBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) { BottomNavigationBar(navController) }
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },

        )
}


/**
 * TODO - замінити дефолт на Monaday
 */

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination =Routes.AddItemPlaning.toString()) {
        animationScreens(NavigationItem.Monday.route) {
            MondayScreen(navController)
        }
        animationScreens(NavigationItem.Planning.route) {
            PlaningScreen(navController)
        }
        animationScreens(NavigationItem.Tracking.route) {
            TrackingScreen()
        }
        animationScreens(NavigationItem.Menu.route) {
            MenuScreen()
        }

        slideInComposable(Routes.AddItemPlaning.toString()) {
            AddItemPlaningScreen(
                navController = navController
            )
        }

    }
}


