package pl.karol202.smartwallet.ui.compose.transactions

import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pl.karol202.smartwallet.presentation.viewdata.TransactionViewData

@Composable
fun TransactionsList(transactions: List<TransactionViewData>)
{
	LazyColumnFor(items = transactions) { transaction ->
		TransactionItem(transaction = transaction)
	}
}

@Preview
@Composable
fun TransactionsListPreview()
{
	TransactionsList(transactions = listOf(TransactionViewData(0, TransactionViewData.Type.EXPENSE, 30.0),
	                                       TransactionViewData(1, TransactionViewData.Type.EXPENSE, 1.97)))
}