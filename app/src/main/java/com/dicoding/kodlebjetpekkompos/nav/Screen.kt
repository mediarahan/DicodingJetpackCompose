package com.dicoding.kodlebjetpekkompos.nav

sealed class Screen (val route: String){
    object Home : Screen("Home")
    object Detail : Screen ("home/{contactId}") {
        fun createRoute(contactId: Long) = "home/$contactId"
    }
    object Profile : Screen ("Profile")
}