package pl.karol202.smartwallet.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pl.karol202.smartwallet.ui.compose.screen.TransactionsScreen
import pl.karol202.smartwallet.ui.compose.theme.AppTheme

@Preview
@Composable
fun App()
{
	AppTheme {
		TransactionsScreen()
	}
}