package ru.agapov.musiciansdiary.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.agapov.musiciansdiary.ui.screens.EntryDetailScreen
import ru.agapov.musiciansdiary.ui.screens.EntryFormScreen
import ru.agapov.musiciansdiary.ui.screens.HomeScreen

@Composable
fun AppNavigation(
    viewModel: EntryViewModel = viewModel()
) {
    val navController = rememberNavController()
    val entries by viewModel.entries.collectAsState()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                entries = entries,
                onEntryClick = { entry ->
                    navController.navigate("detail/${entry.id}")
                },
                onCreateClick = {
                    navController.navigate("form")
                }
            )
        }

        composable(
            route = "detail/{entryId}",
            arguments = listOf(navArgument("entryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId") ?: return@composable
            val entry = entries.find { it.id == entryId }

            if (entry != null) {
                EntryDetailScreen(
                    entry = entry,
                    onBackClick = { navController.popBackStack() },
                    onEditClick = { navController.navigate("form?entryId=${entry.id}") },
                    onDeleteClick = {
                        viewModel.deleteEntry(entry)
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            route = "form?entryId={entryId}",
            arguments = listOf(navArgument("entryId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")
            val entry = entries.find { it.id == entryId }

            EntryFormScreen(
                initialEntry = entry,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { savedEntry ->
                    if (entry == null) {
                        viewModel.addEntry(savedEntry)
                    } else {
                        viewModel.updateEntry(savedEntry)
                    }
                    navController.popBackStack()
                }
            )
        }
    }
}