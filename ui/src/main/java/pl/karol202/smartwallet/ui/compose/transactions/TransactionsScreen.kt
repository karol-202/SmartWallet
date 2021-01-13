package pl.karol202.smartwallet.ui.compose.transactions

import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import pl.karol202.smartwallet.presentation.viewdata.TransactionViewData
import pl.karol202.smartwallet.ui.compose.AppScreen
import pl.karol202.smartwallet.ui.viewmodel.AndroidTransactionsViewModel

@Composable
fun TransactionsScreen(transactionsViewModel: AndroidTransactionsViewModel,
                       scaffoldState: ScaffoldState)
{
	val allTransactions by transactionsViewModel.allTransactions.collectAsState()

	Scaffold(
		topBar = {
			TopAppBar(title = {
				Text(text = stringResource(id = AppScreen.TRANSACTIONS.titleRes))
			})
		},
		scaffoldState = scaffoldState,
		bodyContent = {
			TransactionsScreenContent(allTransactions)
		},
	)
}

@Composable
private fun TransactionsScreenContent(transactions: List<TransactionViewData>)
{
	TransactionsList(transactions = transactions)
}