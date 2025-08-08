package com.example.rickandmorty.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.rickandmorty.ui.screens.characters.*
import com.example.rickandmorty.ui.screens.episodes.*
import com.example.rickandmorty.ui.screens.locations.*
import com.example.rickandmorty.ui.screens.favorite.FavoritesScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val items = listOf(
        Triple(Screen.Characters, "Characters", Icons.Default.Person),
        Triple(Screen.Episodes, "Episodes", Icons.Default.List),
        Triple(Screen.Locations, "Locations", Icons.Default.Place),
    )

    var bottomBarVisible by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisible,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                BottomNavigationBar(navController, items)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Characters.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Characters.route,
                enterTransition = { slideInHorizontally(tween(300)) { it } },
                exitTransition = { slideOutHorizontally(tween(300)) { -it } },
                popEnterTransition = { slideInHorizontally(tween(300)) { -it } },
                popExitTransition = { slideOutHorizontally(tween(300)) { it } }
            ) {
                CharactersScreen(navController) { visible -> bottomBarVisible = visible }
            }

            composable(
                route = Screen.CharacterDetail.route,
                arguments = listOf(navArgument("characterId") { type = NavType.IntType }),
                enterTransition = { slideInVertically(tween(300)) { it } },
                exitTransition = { slideOutVertically(tween(300)) { -it } },
                popEnterTransition = { slideInVertically(tween(300)) { -it } },
                popExitTransition = { slideOutVertically(tween(300)) { it } }
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("characterId") ?: 0
                CharacterDetailScreen(navController, id)
            }

            composable(
                route = Screen.Episodes.route,
                enterTransition = { slideInHorizontally(tween(300)) { it } },
                exitTransition = { slideOutHorizontally(tween(300)) { -it } },
                popEnterTransition = { slideInHorizontally(tween(300)) { -it } },
                popExitTransition = { slideOutHorizontally(tween(300)) { it } }
            ) {
                EpisodesScreen(navController) { visible -> bottomBarVisible = visible }
            }

            composable(
                route = Screen.EpisodeDetail.route,
                arguments = listOf(navArgument("episodeId") { type = NavType.IntType }),
                enterTransition = { slideInVertically(tween(300)) { it } },
                exitTransition = { slideOutVertically(tween(300)) { -it } },
                popEnterTransition = { slideInVertically(tween(300)) { -it } },
                popExitTransition = { slideOutVertically(tween(300)) { it } }
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("episodeId") ?: 0
                EpisodeDetailScreen(navController, id)
            }

            composable(
                route = Screen.Locations.route,
                enterTransition = { slideInHorizontally(tween(300)) { it } },
                exitTransition = { slideOutHorizontally(tween(300)) { -it } },
                popEnterTransition = { slideInHorizontally(tween(300)) { -it } },
                popExitTransition = { slideOutHorizontally(tween(300)) { it } }
            ) {
                LocationsScreen(navController) { visible -> bottomBarVisible = visible }
            }

            composable(
                route = Screen.LocationDetail.route,
                arguments = listOf(navArgument("locationId") { type = NavType.IntType }),
                enterTransition = { slideInVertically(tween(300)) { it } },
                exitTransition = { slideOutVertically(tween(300)) { -it } },
                popEnterTransition = { slideInVertically(tween(300)) { -it } },
                popExitTransition = { slideOutVertically(tween(300)) { it } }
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("locationId") ?: 0
                LocationDetailScreen(navController, id)
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Triple<Screen, String, ImageVector>>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { (screen, label, icon) ->
            val selected = currentRoute?.startsWith(screen.route) == true
            NavigationBarItem(
                icon = { AnimatedIcon(icon, selected) },
                label = { Text(label) },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Characters.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun AnimatedIcon(icon: ImageVector, isSelected: Boolean) {
    val scale by animateFloatAsState(if (isSelected) 1.3f else 1f)
    Icon(icon, contentDescription = null, modifier = Modifier.scale(scale))
}