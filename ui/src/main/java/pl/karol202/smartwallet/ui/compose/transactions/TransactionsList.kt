package pl.karol202.smartwallet.ui.compose.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.karol202.smartwallet.presentation.viewdata.TransactionItemViewData

@Composable
fun TransactionsList(transactions: List<TransactionItemViewData>,
                     onEdit: (Long) -> Unit)
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
