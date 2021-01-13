package pl.karol202.smartwallet.ui.compose.transactions

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pl.karol202.smartwallet.presentation.viewdata.TransactionViewData

@Composable
fun TransactionsList(transactions: List<TransactionViewData>)
{
	LazyColumn {
		items(items = transactions) { transaction ->
			TransactionItem(transaction = transaction)
		}
	}
}
