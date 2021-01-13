package pl.karol202.smartwallet.ui.compose

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel
import pl.karol202.smartwallet.ui.compose.theme.AppTheme
import pl.karol202.smartwallet.ui.compose.transactions.TransactionsScreen

@Preview
@Composable
fun App()
{
	val scaffoldState = rememberScaffoldState()

	AppTheme {
		NavHost(
			navController = rememberNavController(),
			startDestination = AppScreen.default.route,
			builder = {
				for(screen in AppScreen.all)
					composable(screen.route) {
						when(screen)
						{
							AppScreen.TRANSACTIONS ->
								TransactionsScreen(
									transactionsViewModel = getViewModel(),
									scaffoldState = scaffoldState
								)
						}
					}
			}
		)
	}
}