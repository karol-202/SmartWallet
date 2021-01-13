package pl.karol202.smartwallet.ui.compose.transactions

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import pl.karol202.smartwallet.presentation.viewdata.TransactionViewData

@Composable
fun TransactionItem(transaction: TransactionViewData)
{
	Text(text = transaction.amount.toString())
}