package com.soft.dailynotes.presentation.ui.navigation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.soft.dailynotes.presentation.ui.notes.about.AboutUs
import com.soft.dailynotes.presentation.ui.notes.archive.ArchiveScreen
import com.soft.dailynotes.presentation.ui.notes.details.NoteDetails
import com.soft.dailynotes.presentation.ui.notes.details.NotesDetailsDestination
import com.soft.dailynotes.presentation.ui.notes.home.HomeViewModel
import com.soft.dailynotes.presentation.ui.notes.home.NoteHomeScreen
import com.soft.dailynotes.presentation.ui.notes.settings.SettingsScreen
import com.soft.dailynotes.presentation.ui.utils.Constants.ABOUT
import com.soft.dailynotes.presentation.ui.utils.NavigationItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyNotesNavigationGraph() {
    val context = LocalContext.current as Activity
    val navHostController = rememberNavController()
    val activity = (LocalContext.current as? Activity)
    val notesHomeViewModel: HomeViewModel = viewModel()



    NavHost(
        navController = navHostController,
        startDestination = NavigationItem.navigationItems[0].title
    ) {

        composable(
            route = NavigationItem.navigationItems[0].title,
            // arguments = listOf(navArgument(HomeDestination.userIdArgs){type = NavType.StringType})
        ) {
            NoteHomeScreen(
                homeNoteViewModel = notesHomeViewModel,
                onNoteClick = { navHostController.navigate("${NotesDetailsDestination.route}/${it}") },
                onNavigateToDetails = {
                    navHostController.navigate("${NotesDetailsDestination.route}/${it}")
                }, onNavigateUp = {
                    activity?.finish()
                }, navController = navHostController
            )
        }
        composable(route = NavigationItem.navigationItems[1].title) {
            ArchiveScreen(
                navController = navHostController,
                onNavigateUp = { navHostController.popBackStack() })
        }
        composable(route = NavigationItem.navigationItems[2].title) {
            SettingsScreen(
                navController = navHostController,
                onNavigateUp = { navHostController.popBackStack() },
                onAboutUsClick = { navHostController.navigate(ABOUT) })
        }
        composable(route = ABOUT) {
            AboutUs(
                navController = navHostController,
                onNavigateUp = { navHostController.popBackStack() })
        }
        composable(
            route = NotesDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(NotesDetailsDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) {
            NoteDetails() {
                navHostController.popBackStack()
            }
        }

    }


}