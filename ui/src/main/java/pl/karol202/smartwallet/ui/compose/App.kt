package pl.karol202.smartwallet.ui.compose

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import org.koin.androidx.compose.getViewModel
import pl.karol202.smartwallet.presentation.viewmodel.TransactionEditViewModel
import pl.karol202.smartwallet.ui.compose.theme.AppTheme
import pl.karol202.smartwallet.ui.compose.transactionedit.TransactionEditScreen
import pl.karol202.smartwallet.ui.compose.transactions.TransactionsScreen

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
		Route.TransactionCreate ->
			TransactionEditScreen(
				transactionEditViewModel = getViewModel()
			)
		Route.TransactionEdit ->
			TransactionEditScreen(
				transactionEditViewModel = getViewModel(),
				transactionId = navEntry.arguments?.getLong(Route.TransactionEdit.ARG_TRANSACTION_ID)
			)
	}
}
