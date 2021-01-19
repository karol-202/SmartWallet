package pl.karol202.smartwallet.ui.compose

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel
import pl.karol202.smartwallet.ui.compose.screens.categories.CategoriesScreen
import pl.karol202.smartwallet.ui.compose.screens.categoryedit.CategoryEditScreen
import pl.karol202.smartwallet.ui.compose.screens.subcategoryedit.SubcategoryEditScreen
import pl.karol202.smartwallet.ui.compose.screens.transactionedit.TransactionEditScreen
import pl.karol202.smartwallet.ui.compose.screens.transactions.TransactionsScreen
import pl.karol202.smartwallet.ui.compose.theme.AppTheme

@Preview
@Composable
fun App()
{
	val navController = rememberNavController()
	val scaffoldState = rememberScaffoldState()

	AppTheme {
		NavHost(
			navController = navController,
			startDestination = Route.default.route,
			builder = {
				for(screen in Route.values)
					addScreen(
						navController = navController,
						scaffoldState = scaffoldState,
						screen = screen
					)
			}
		)
	}
}

private fun NavGraphBuilder.addScreen(navController: NavHostController,
                                      scaffoldState: ScaffoldState,
                                      screen: Route) = composable(screen.route, screen.args) { navEntry ->
	when(screen)
	{
		Route.Transactions ->
			TransactionsScreen(
				transactionsViewModel = getViewModel(),
				scaffoldState = scaffoldState,
				onTransactionCreate = {
					navController.navigate(Route.TransactionEdit.constructRoute())
				},
				onTransactionEdit = { transactionId ->
					navController.navigate(Route.TransactionEdit.constructRoute(transactionId))
				}
			)
		Route.TransactionEdit ->
			TransactionEditScreen(
				transactionEditViewModel = getViewModel(),
				transactionId = navEntry.getStringArgument(Route.TransactionEdit.ARG_TRANSACTION_ID),
				onNavigateBack = { navController.popBackStack() }
			)
		Route.Categories ->
			CategoriesScreen(
				categoriesViewModel = getViewModel(),
				scaffoldState = scaffoldState,
				onCategoryCreate = {
					navController.navigate(Route.CategoryEdit.constructRoute())
				},
				onCategoryEdit = { categoryId ->
					navController.navigate(Route.CategoryEdit.constructRoute(categoryId))
				}
			)
		Route.CategoryEdit ->
			CategoryEditScreen(
				categoryEditViewModel = getViewModel(),
				categoryId = navEntry.getStringArgument(Route.CategoryEdit.ARG_CATEGORY_ID),
				onNavigateBack = { navController.popBackStack() },
				onSubcategoryCreate = { categoryId ->
					navController.navigate(Route.SubcategoryEdit.constructRoute(categoryId))
				},
				onSubcategoryEdit = { categoryId, subcategoryId ->
					navController.navigate(Route.SubcategoryEdit.constructRoute(categoryId, subcategoryId))
				}
			)
		Route.SubcategoryEdit ->
			SubcategoryEditScreen(
				subcategoryEditViewModel = getViewModel(),
				categoryId = navEntry.requireStringArgument(Route.SubcategoryEdit.ARG_CATEGORY_ID),
				subcategoryId = navEntry.getStringArgument(Route.SubcategoryEdit.ARG_SUBCATEGORY_ID),
				onNavigateBack = { navController.popBackStack() }
			)
	}
}

private fun NavBackStackEntry.getStringArgument(key: String) = arguments?.getString(key)

private fun NavBackStackEntry.requireStringArgument(key: String) =
		getStringArgument(key) ?: throw IllegalStateException("Argument $key not found")
