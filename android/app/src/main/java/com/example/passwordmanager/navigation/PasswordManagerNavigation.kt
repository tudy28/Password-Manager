package com.example.passwordmanager.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navArgument
import com.example.passwordmanager.accounts.AccountsScreen
import com.example.passwordmanager.accounts.viewmodel.AccountsViewModel
import com.example.passwordmanager.add_account.AddAccountScreen
import com.example.passwordmanager.details.AccountDetailsScreen
import com.example.passwordmanager.edit_account.EditAccountScreen
import com.example.passwordmanager.navigation.Screen.*


@Composable
fun PasswordManagerNavigation(viewModel: AccountsViewModel) {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Accounts.route) {
        composable(
            route = Accounts.route
        ) {
            AccountsScreen(
                onAccountClick = { selectedAccount ->
                    navController.navigate("${AccountDetails.route}/$selectedAccount")
                },
                onAddAccountClick = {navController.navigate(AccountAdd.route)},
                viewModel = viewModel
            )
        }
        composable(
            route = "${AccountDetails.route}/{selectedAccount}",
            arguments = listOf(navArgument("selectedAccount") { type = NavType.LongType })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getLong("selectedAccount")?.let { account ->
                AccountDetailsScreen(
                    selectedAccountID = account,
                    viewModel = viewModel,
                    onEditClick = {
                        navController.navigate("${AccountEdit.route}/$account")
                    },
                    navController = navController
                )
            }
        }
        composable(
            route = AccountAdd.route
        ) {
            AddAccountScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = "${AccountEdit.route}/{selectedAccount}" ,
            arguments = listOf(navArgument("selectedAccount") { type = NavType.LongType })
        ){ navBackStackEntry ->
            navBackStackEntry.arguments?.getLong("selectedAccount")?.let { account ->
                EditAccountScreen(
                    selectedAccountID = account,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }

    }
}