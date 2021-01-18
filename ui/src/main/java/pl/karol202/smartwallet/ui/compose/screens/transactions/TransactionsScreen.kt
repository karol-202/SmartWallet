package pl.karol202.smartwallet.ui.compose.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.karol202.smartwallet.presentation.viewdata.TransactionItemViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.viewmodel.AndroidTransactionsViewModel

@Composable
fun TransactionsScreen(transactionsViewModel: AndroidTransactionsViewModel,
                       scaffoldState: ScaffoldState,
                       onTransactionCreate: () -> Unit,
                       onTransactionEdit: (String) -> Unit)
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
			TransactionsScreenContent(
				transactions = allTransactions,
				onTransactionEdit = onTransactionEdit
			)
		},
	)
}

@Composable
private fun TransactionsScreenContent(transactions: List<TransactionItemViewData>,
                                      onTransactionEdit: (String) -> Unit)
{
	TransactionsList(
		transactions = transactions,
		onEdit = onTransactionEdit
	)
}

@Composable
fun TransactionsList(transactions: List<TransactionItemViewData>,
                     onEdit: (String) -> Unit)
{
	LazyColumn {
		items(items = transactions) { transaction ->
			TransactionItem(
				transaction = transaction,
				onEdit = { onEdit(transaction.id) }
			)
		}
	}
}

@Composable
fun TransactionItem(transaction: TransactionItemViewData,
                    onEdit: () -> Unit)
{
	Row(
		modifier = Modifier
				.fillMaxWidth()
				.clickable(onClick = {})
				.padding(start = 24.dp, end = 8.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(text = transaction.amount.toString())
		IconButton(
			onClick = onEdit,
			content = {
				Icon(Icons.Filled.Edit)
			}
		)
	}
}
