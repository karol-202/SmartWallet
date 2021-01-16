package pl.karol202.smartwallet.ui.compose.transactions

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import pl.karol202.smartwallet.presentation.viewdata.TransactionItemViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.viewmodel.AndroidTransactionsViewModel

@Composable
fun TransactionsScreen(transactionsViewModel: AndroidTransactionsViewModel,
                       scaffoldState: ScaffoldState,
                       onTransactionCreate: () -> Unit,
                       onTransactionEdit: (Long) -> Unit)
{
	val allTransactions by transactionsViewModel.allTransactions.collectAsState()

	Scaffold(
		scaffoldState = scaffoldState,
		topBar = {
			TopAppBar(
				title = {
					Text(text = stringResource(R.string.screen_transactions))
				}
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = { onTransactionCreate() },
				content = {
					Icon(Icons.Default.Add)
				}
			)
		},
		bodyContent = {
			TransactionsScreenContent(allTransactions)
		},
	)
}

@Composable
private fun TransactionsScreenContent(transactions: List<TransactionItemViewData>)
{
	TransactionsList(transactions = transactions)
}