package pl.karol202.smartwallet.ui.compose.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pl.karol202.smartwallet.ui.R

@Preview
@Composable
fun TransactionsScreen()
{
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = stringResource(id = R.string.app_name))
				}
			)
		},
		bodyContent = {

		},
	)
}