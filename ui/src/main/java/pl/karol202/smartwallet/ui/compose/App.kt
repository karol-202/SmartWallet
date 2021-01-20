package pl.karol202.smartwallet.ui.compose

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import org.koin.androidx.compose.getViewModel
import pl.karol202.smartwallet.ui.compose.screens.categories.CategoriesScreen
import pl.karol202.smartwallet.ui.compose.screens.categoryedit.CategoryEditScreen
import pl.karol202.smartwallet.ui.compose.screens.subcategoryedit.SubcategoryEditScreen
import pl.karol202.smartwallet.ui.compose.screens.transactionedit.TransactionEditScreen
import pl.karol202.smartwallet.ui.compose.screens.transactions.TransactionsScreen
import pl.karol202.smartwallet.ui.compose.theme.AppTheme

val AmbientRoute = ambientOf<Route>()

@Preview
@Composable
fun App()
{
	val navController = rememberNavController()
	val scaffoldState = rememberScaffoldState()

	AppTheme {
		NavHost(
			navController = navController,
			startDestination = Routes.default.route,
			builder = {
				for(route in Routes.values)
					addScreen(
						scaffoldState = scaffoldState,
						route = route,
						onNavigate = { navController.navigate(it) },
						onNavigateTopLevel = {
							navController.navigate(it) {
								popUpTo(navController.graph.startDestination) { inclusive = true }
								launchSingleTop = true
							}
						},
						onNavigateBack = { navController.popBackStack() }
					)
			}
		)
	}
}

private fun NavGraphBuilder.addScreen(scaffoldState: ScaffoldState,
                                      route: Route,
                                      onNavigate: (String) -> Unit,
                                      onNavigateTopLevel: (String) -> Unit,
                                      onNavigateBack: () -> Unit) = composable(route.route, route.args) { navEntry ->

	Providers(AmbientRoute provides route) {
		when(route)
		{
			Routes.Transactions ->
				TransactionsScreen(
					transactionsViewModel = getViewModel(),
					scaffoldState = scaffoldState,
					onRouteSelect = { onNavigateTopLevel(it.constructRoute()) },
					onTransactionCreate = {
						onNavigate(Routes.TransactionEdit.constructRoute())
					},
					onTransactionEdit = { transactionId ->
						onNavigate(Routes.TransactionEdit.constructRoute(transactionId))
					}
				)
			Routes.TransactionEdit ->
				TransactionEditScreen(
					transactionEditViewModel = getViewModel(),
					transactionId = navEntry.getStringArgument(Routes.TransactionEdit.ARG_TRANSACTION_ID),
					onNavigateBack = onNavigateBack
				)
			Routes.Categories ->
				CategoriesScreen(
					categoriesViewModel = getViewModel(),
					scaffoldState = scaffoldState,
					onRouteSelect = { onNavigateTopLevel(it.constructRoute()) },
					onCategoryCreate = {
						onNavigate(Routes.CategoryEdit.constructRoute())
					},
					onCategoryEdit = { categoryId ->
						onNavigate(Routes.CategoryEdit.constructRoute(categoryId))
					}
				)
			Routes.CategoryEdit ->
				CategoryEditScreen(
					categoryEditViewModel = getViewModel(),
					categoryId = navEntry.getStringArgument(Routes.CategoryEdit.ARG_CATEGORY_ID),
					onNavigateBack = onNavigateBack,
					onSubcategoryCreate = { categoryId ->
						onNavigate(Routes.SubcategoryEdit.constructRoute(categoryId))
					},
					onSubcategoryEdit = { categoryId, subcategoryId ->
						onNavigate(Routes.SubcategoryEdit.constructRoute(categoryId, subcategoryId))
					}
				)
			Routes.SubcategoryEdit ->
				SubcategoryEditScreen(
					subcategoryEditViewModel = getViewModel(),
					categoryId = navEntry.requireStringArgument(Routes.SubcategoryEdit.ARG_CATEGORY_ID),
					subcategoryId = navEntry.getStringArgument(Routes.SubcategoryEdit.ARG_SUBCATEGORY_ID),
					onNavigateBack = onNavigateBack
				)
		}
	}
}

private fun NavBackStackEntry.getStringArgument(key: String) = arguments?.getString(key)

private fun NavBackStackEntry.requireStringArgument(key: String) =
		getStringArgument(key) ?: throw IllegalStateException("Argument $key not found")
