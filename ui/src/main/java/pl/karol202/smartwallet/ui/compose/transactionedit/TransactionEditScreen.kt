package pl.karol202.smartwallet.ui.compose.transactionedit

import androidx.compose.foundation.currentTextStyle
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Toll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.karol202.smartwallet.presentation.viewdata.TransactionEditViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionTypeViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.theme.AppColors
import pl.karol202.smartwallet.ui.compose.view.RadioButtonWithText
import pl.karol202.smartwallet.ui.viewmodel.AndroidTransactionEditViewModel

@Composable
fun TransactionEditScreen(transactionEditViewModel: AndroidTransactionEditViewModel,
                          transactionId: Long? = null)
{
	LaunchedEffect(transactionId) {
		if(transactionId == null) transactionEditViewModel.editNewTransaction()
		else transactionEditViewModel.editExistingTransaction(transactionId)
	}

	val editedTransactionState by transactionEditViewModel.editedTransaction.collectAsState()
	val editedTransaction = editedTransactionState ?: return

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = stringResource(R.string.screen_transaction_edit))
				}
			)
		},
		bodyContent = {
			TransactionEditScreenContent(
				transaction = editedTransaction,
				setTransactionType = { transactionEditViewModel.setTransactionType(it) },
				setTransaction = { transactionEditViewModel.setTransaction(it) }
			)
		},
	)
}

@Composable
private fun TransactionEditScreenContent(transaction: TransactionEditViewData,
                                         setTransactionType: (TransactionTypeViewData) -> Unit,
                                         setTransaction: (TransactionEditViewData) -> Unit)
{
	Column(modifier = Modifier.padding(horizontal = 24.dp)) {
		TransactionTypeSelector(
			transaction = transaction,
			setTransactionType = setTransactionType
		)
		when(transaction)
		{
			is TransactionEditViewData.Expense -> TransactionDetailsExpense(
				transaction = transaction,
				setTransaction = setTransaction
			)
			is TransactionEditViewData.Income -> TransactionDetailsIncome(
				transaction = transaction,
				setTransaction = setTransaction
			)
		}
	}
}

@Composable
private fun TransactionTypeSelector(transaction: TransactionEditViewData,
                                    setTransactionType: (TransactionTypeViewData) -> Unit)
{
	Row(
		modifier = Modifier.padding(vertical = 16.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		RadioButtonWithText(
			text = stringResource(R.string.transaction_type_expense),
			selected = transaction is TransactionEditViewData.Expense,
			onClick = { setTransactionType(TransactionTypeViewData.EXPENSE) }
		)
		RadioButtonWithText(
			text = stringResource(R.string.transaction_type_income),
			selected = transaction is TransactionEditViewData.Income,
			onClick = { setTransactionType(TransactionTypeViewData.INCOME) }
		)
	}
}

@Composable
private fun TransactionDetailsExpense(transaction: TransactionEditViewData.Expense,
                                      setTransaction: (TransactionEditViewData) -> Unit)
{
	TransactionAmount(
		initialValue = transaction.amount,
		onChange = { setTransaction(transaction.withAmount(it)) },
		transactionType = TransactionTypeViewData.EXPENSE
	)
}

@Composable
private fun TransactionDetailsIncome(transaction: TransactionEditViewData.Income,
                                     setTransaction: (TransactionEditViewData) -> Unit)
{
	TransactionAmount(
		initialValue = transaction.amount,
		onChange = { setTransaction(transaction.withAmount(it)) },
		transactionType = TransactionTypeViewData.INCOME
	)
}

@Composable
private fun TransactionAmount(initialValue: Double,
                              onChange: (Double) -> Unit,
                              transactionType: TransactionTypeViewData)
{
	var value by remember { mutableStateOf(initialValue.toString()) }

	val textColor = when(transactionType)
	{
		TransactionTypeViewData.EXPENSE -> AppColors.textTransactionAmountExpense
		TransactionTypeViewData.INCOME -> AppColors.textTransactionAmountIncome
	}

	TextField(
		value = value,
		onValueChange = {
			value = it
			it.toDoubleOrNull()?.let(onChange)
		},
		modifier = Modifier.fillMaxWidth(),
		textStyle = MaterialTheme.typography.h4.copy(
			color = textColor
		),
		label = {
			Text(text = stringResource(R.string.transaction_edit_amount))
		},
		leadingIcon = {
			Icon(Icons.Filled.Toll)
		}
	)
}
