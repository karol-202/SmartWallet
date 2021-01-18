package pl.karol202.smartwallet.ui.compose

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import org.koin.androidx.compose.getViewModel
import pl.karol202.smartwallet.ui.compose.screens.categories.CategoriesScreen
import pl.karol202.smartwallet.ui.compose.theme.AppTheme
import pl.karol202.smartwallet.ui.compose.screens.transactionedit.TransactionEditScreen
import pl.karol202.smartwallet.ui.compose.screens.transactions.TransactionsScreen

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
					navController.navigate(Route.TransactionCreate.constructRoute())
				},
				onTransactionEdit = { transactionId ->
					navController.navigate(Route.TransactionEdit.constructRoute(transactionId))
				}
			)
		Route.TransactionCreate, Route.TransactionEdit ->
			TransactionEditScreen(
				transactionEditViewModel = getViewModel(),
				transactionId =
					if(screen is Route.TransactionCreate) null
					else navEntry.arguments?.getString(Route.TransactionEdit.ARG_TRANSACTION_ID),
				onNavigateBack = {
					navController.popBackStack()
				}
			)
		Route.Categories ->
			CategoriesScreen(
				categoriesViewModel = getViewModel(),
				scaffoldState = scaffoldState
			)
	}
}
