package com.liebersonsantos.training.newsappcompose.presentation.newsnavigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.liebersonsantos.training.newsappcompose.R
import com.liebersonsantos.training.newsappcompose.domain.model.Article
import com.liebersonsantos.training.newsappcompose.presentation.bookmark.BookmarkScreen
import com.liebersonsantos.training.newsappcompose.presentation.bookmark.BookmarkState
import com.liebersonsantos.training.newsappcompose.presentation.bookmark.BookmarkViewModel
import com.liebersonsantos.training.newsappcompose.presentation.detail.DetailEvent
import com.liebersonsantos.training.newsappcompose.presentation.detail.DetailScreen
import com.liebersonsantos.training.newsappcompose.presentation.detail.DetailsViewModel
import com.liebersonsantos.training.newsappcompose.presentation.home.HomeScreen
import com.liebersonsantos.training.newsappcompose.presentation.home.HomeViewModel
import com.liebersonsantos.training.newsappcompose.presentation.navgraph.Route
import com.liebersonsantos.training.newsappcompose.presentation.newsnavigator.components.BottomNavigationItem
import com.liebersonsantos.training.newsappcompose.presentation.newsnavigator.components.NewsBottomNavigation
import com.liebersonsantos.training.newsappcompose.presentation.search.SearchScreen
import com.liebersonsantos.training.newsappcompose.presentation.search.SearchState
import com.liebersonsantos.training.newsappcompose.presentation.search.SearchViewModel

const val HOME = "Home"
const val SEARCH = "Search"
const val BOOKMARK = "Bookmark"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator() {
    val bottomNavigationItems = bottomNavigationItems()
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = selectedItem(backStackState)

    val isBottomBarVisible =  remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNav(bottomNavigationItems, selectedItem, navController)
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()

                NavigateToHome(articles, navController)
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                NavigateToSearchScreen(state, viewModel, navController)
            }
            composable(route = Route.DetailScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.onEvent(DetailEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        NavigateToDetails(article, viewModel, navController)
                    }
            }
            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                NavigateToBookmark(state, navController)
            }
        }
    }
}

@Composable
private fun bottomNavigationItems() = remember {
    listOf(
        BottomNavigationItem(icon = R.drawable.ic_home, text = HOME),
        BottomNavigationItem(icon = R.drawable.ic_search, text = SEARCH),
        BottomNavigationItem(icon = R.drawable.ic_bookmark, text = BOOKMARK)
    )
}

@Composable
private fun NavigateToBookmark(
    state: BookmarkState,
    navController: NavHostController
) {
    BookmarkScreen(
        state = state,
        navigateToDetails = { article ->
            navigateToDetails(
                navController = navController,
                article = article
            )
        })
}

@Composable
private fun NavigateToDetails(
    article: Article,
    viewModel: DetailsViewModel,
    navController: NavHostController
) {
    DetailScreen(
        article = article,
        event = viewModel::onEvent,
        navigationUP = { navController.navigateUp() })
}

@Composable
private fun NavigateToSearchScreen(
    state: SearchState,
    viewModel: SearchViewModel,
    navController: NavHostController
) {
    SearchScreen(
        state = state,
        event = viewModel::onEvent,
        navigateToDetails = { article ->
            navigateToDetails(navController = navController, article = article)
        }
    )
}

@Composable
private fun NavigateToHome(
    articles: LazyPagingItems<Article>,
    navController: NavHostController
) {
    HomeScreen(
        articles = articles,
        navigateToSearch = {
            navigateToTap(
                navController = navController,
                route = Route.SearchScreen.route
            )
        },
        navigateToDetails = { article ->
            navigateToDetails(
                navController = navController,
                article = article
            )
        }
    )
}

@Composable
private fun NewsBottomNav(
    bottomNavigationItems: List<BottomNavigationItem>,
    selectedItem: Int,
    navController: NavHostController
) {
    NewsBottomNavigation(
        items = bottomNavigationItems,
        selected = selectedItem,
        onItemClick = { index ->
            when (index) {
                0 -> navigateToTap(
                    navController = navController,
                    route = Route.HomeScreen.route
                )

                1 -> navigateToTap(
                    navController = navController,
                    route = Route.SearchScreen.route
                )

                2 -> navigateToTap(
                    navController = navController,
                    route = Route.BookmarkScreen.route
                )
            }
        }
    )
}

@Composable
private fun selectedItem(backStackState: NavBackStackEntry?) =
    remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }

fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(route = Route.DetailScreen.route)
}