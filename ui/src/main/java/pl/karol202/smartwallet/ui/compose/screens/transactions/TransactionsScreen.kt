package pl.karol202.smartwallet.ui.compose.screens.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pl.karol202.smartwallet.presentation.viewdata.TransactionItemViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.Route
import pl.karol202.smartwallet.ui.compose.drawer.DrawerContent
import pl.karol202.smartwallet.ui.compose.view.AppBarIcon
import pl.karol202.smartwallet.ui.viewmodel.AndroidTransactionsViewModel

@Composable
fun TransactionsScreen(transactionsViewModel: AndroidTransactionsViewModel,
                       scaffoldState: ScaffoldState,
                       onRouteSelect: (Route.TopLevel) -> Unit,
                       onTransactionCreate: () -> Unit,
                       onTransactionEdit: (String) -> Unit)
{
	val allTransactions by transactionsViewModel.allTransactions.collectAsState(emptyList())

	Scaffold(
		scaffoldState = scaffoldState,
		topBar = {
			TopAppBar(
				title = {
					Text(text = stringResource(R.string.screen_transactions))
				},
				navigationIcon = {
					AppBarIcon(
						imageVector = Icons.Filled.Menu,
						onClick = { scaffoldState.drawerState.open() }
					)
				}
			)
		},
		drawerContent = {
			DrawerContent(
				onRouteSelect = onRouteSelect,
				onDrawerClose = { scaffoldState.drawerState.close() }
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
private fun TransactionsList(transactions: List<TransactionItemViewData>,
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
private fun TransactionItem(transaction: TransactionItemViewData,
                            onEdit: () -> Unit)
{
	when(transaction)
	{
		is TransactionItemViewData.Expense -> TransactionItemExpense(transaction = transaction, onEdit = onEdit)
		is TransactionItemViewData.Income -> TransactionItemIncome(transaction = transaction, onEdit = onEdit)
	}
}

@Composable
private fun TransactionItemExpense(transaction: TransactionItemViewData.Expense,
                                   onEdit: () -> Unit)
{
	ListItem(
		modifier = Modifier.clickable(onClick = onEdit),
		text = {
			Text(text = transaction.subcategory.name)
		},
		trailing = {
			Text(text = transaction.amount.toString())
		}
	)
}

@Composable
private fun TransactionItemIncome(transaction: TransactionItemViewData.Income,
                                  onEdit: () -> Unit)
{
	ListItem(
		modifier = Modifier.clickable(onClick = onEdit),
		text = {
			Text(text = transaction.subcategory.name)
		},
		trailing = {
			Text(text = transaction.amount.toString())
		}
	)
}
