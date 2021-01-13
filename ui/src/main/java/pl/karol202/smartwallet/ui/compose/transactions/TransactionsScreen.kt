package pl.karol202.smartwallet.ui.compose.transactions

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import pl.karol202.smartwallet.ui.R

@Composable
fun TransactionsScreen(scaffoldState: ScaffoldState)
{
	Scaffold(
		topBar = {
			TopAppBar(title = {
				Text(text = stringResource(id = R.string.app_name))
			})
		},
		scaffoldState = scaffoldState,
		bodyContent = {

		},
	)
}