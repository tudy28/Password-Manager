package com.example.passwordmanager.navigation

sealed class Screen(val route: String) {
    object Accounts : Screen(route = "accounts")
    object AccountDetails : Screen(route = "details")
    object AccountAdd: Screen(route = "add-account")
    object AccountEdit: Screen(route = "edit-account")
}