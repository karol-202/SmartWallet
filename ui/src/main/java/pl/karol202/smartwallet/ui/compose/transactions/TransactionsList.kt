package pl.karol202.smartwallet.ui.compose.transactions

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.karol202.smartwallet.presentation.viewdata.TransactionItemViewData

@Composable
fun TransactionsList(transactions: List<TransactionItemViewData>)
{
	LazyColumn {
		items(items = transactions) { transaction ->
			TransactionItem(transaction = transaction)
		}
	}
}

@Composable
fun TransactionItem(transaction: TransactionItemViewData)
{
	Text(text = transaction.amount.toString())
}
