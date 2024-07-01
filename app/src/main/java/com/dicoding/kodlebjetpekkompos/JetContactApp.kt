package com.dicoding.kodlebjetpekkompos

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.kodlebjetpekkompos.ui.theme.KodlebJetpekKomposTheme
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.kodlebjetpekkompos.nav.Screen
import com.dicoding.kodlebjetpekkompos.screen.detail.DetailScreen
import com.dicoding.kodlebjetpekkompos.screen.home.HomeScreen
import androidx.compose.ui.Modifier
import com.dicoding.kodlebjetpekkompos.nav.NavigationItem
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.dicoding.kodlebjetpekkompos.R.string
import com.dicoding.kodlebjetpekkompos.screen.profile.ProfileScreen

@Composable
fun JetContactApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { contactId ->
                        navController.navigate(Screen.Detail.createRoute(contactId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen (
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("contactId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("contactId") ?: -1L
                DetailScreen(
                    contactId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(string.home_text),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(string.profile_text),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "about_page"
                    )
                },
                label = {Text(item.title)},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactListItemPreview() {
    KodlebJetpekKomposTheme {
        JetContactApp()
    }
}