package pl.karol202.smartwallet.ui.compose.screens.transactionedit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.datepicker
import kotlinx.coroutines.flow.collect
import pl.karol202.smartwallet.presentation.viewdata.CategoryWithSubcategoriesItemViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionEditViewData
import pl.karol202.smartwallet.presentation.viewdata.TransactionEditViewData.*
import pl.karol202.smartwallet.presentation.viewdata.TransactionTypeViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.theme.AppColors
import pl.karol202.smartwallet.ui.compose.view.AppBarIcon
import pl.karol202.smartwallet.ui.compose.view.ExposedDropdownMenu
import pl.karol202.smartwallet.ui.compose.view.SimpleAlertDialog
import pl.karol202.smartwallet.ui.compose.view.ToggleButtonGroup
import pl.karol202.smartwallet.ui.viewmodel.AndroidTransactionEditViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun TransactionEditScreen(transactionEditViewModel: AndroidTransactionEditViewModel,
                          transactionId: String? = null,
                          onNavigateBack: () -> Unit)
{
	LaunchedEffect(transactionEditViewModel, transactionId) {
		if(transactionId == null) transactionEditViewModel.editNewTransaction()
		else transactionEditViewModel.editExistingTransaction(transactionId)
	}
	LaunchedEffect(transactionEditViewModel) {
		transactionEditViewModel.finishEvent.collect { onNavigateBack() }
	}

	val availableCategories by transactionEditViewModel.availableCategories.collectAsState(emptyList())
	val editedTransaction = transactionEditViewModel.editedTransaction.collectAsState(null).value ?: return

	var removeDialogVisible by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			TransactionEditScreenAppbar(
				transactionExists = transactionId != null,
				editedTransaction = editedTransaction,
				onNavigateBack = onNavigateBack,
				onRemove = { removeDialogVisible = true }
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					transactionEditViewModel.apply()
				},
				content = {
					Icon(Icons.Filled.Done)
				}
			)
		},
		bodyContent = {
			TransactionEditScreenContent(
				transaction = editedTransaction,
				setTransactionType = { transactionEditViewModel.setTransactionType(it) },
				setTransaction = { transactionEditViewModel.setTransaction(it) },
				categories = availableCategories
			)
		},
	)

	if(removeDialogVisible) TransactionRemoveDialog(
		onConfirm = { transactionEditViewModel.removeTransaction() },
		onDismiss = { removeDialogVisible = false }
	)
}

@Composable
fun TransactionEditScreenAppbar(transactionExists: Boolean,
                                editedTransaction: TransactionEditViewData,
                                onNavigateBack: () -> Unit,
                                onRemove: () -> Unit)
{
	TopAppBar(
		title = {
			Text(
				text = stringResource(
					when(editedTransaction)
					{
						is Expense ->
							if(!transactionExists) R.string.screen_transaction_new_expense
							else R.string.screen_transaction_edit_expense
						is Income ->
							if(!transactionExists) R.string.screen_transaction_new_income
							else R.string.screen_transaction_edit_income
					})
			)
		},
		navigationIcon = {
			AppBarIcon(
				imageVector = Icons.Filled.ArrowBack,
				onClick = { onNavigateBack() }
			)
		},
		actions = {
			AppBarIcon(
				imageVector = Icons.Filled.Delete,
				enabled = transactionExists,
				onClick = onRemove
			)
		}
	)
}

@Composable
private fun TransactionEditScreenContent(transaction: TransactionEditViewData,
                                         setTransactionType: (TransactionTypeViewData) -> Unit,
                                         setTransaction: (TransactionEditViewData) -> Unit,
                                         categories: List<CategoryWithSubcategoriesItemViewData>)
{
	Column {
		TransactionTypeSelector(
			transaction = transaction,
			setTransactionType = setTransactionType
		)
		when(transaction)
		{
			is Expense -> TransactionDetailsExpense(
				transaction = transaction,
				setTransaction = setTransaction,
				categories = categories
			)
			is Income -> TransactionDetailsIncome(
				transaction = transaction,
				setTransaction = setTransaction,
				categories = categories
			)
		}
	}
}

@Composable
private fun TransactionTypeSelector(transaction: TransactionEditViewData,
                                    setTransactionType: (TransactionTypeViewData) -> Unit)
{
	Box(
		modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 24.dp, vertical = 16.dp),
		contentAlignment = Alignment.Center,
		content = {
			ToggleButtonGroup(
				content = {
					item(
						checked = transaction is Expense,
						onClick = { setTransactionType(TransactionTypeViewData.EXPENSE) },
						content = {
							Text(text = stringResource(R.string.transaction_type_expense))
						}
					)
					item(
						checked = transaction is Income,
						onClick = { setTransactionType(TransactionTypeViewData.INCOME) },
						content = {
							Text(text = stringResource(R.string.transaction_type_income))
						}
					)
				}
			)
		}
	)
}

@Composable
private fun TransactionDetailsExpense(transaction: Expense,
                                      setTransaction: (TransactionEditViewData) -> Unit,
                                      categories: List<CategoryWithSubcategoriesItemViewData>)
{
	TransactionAmount(
		initialValue = transaction.amount,
		setAmount = { setTransaction(transaction.withAmount(it)) },
		transactionType = transaction.type
	)
	TransactionSubcategory(
		categories = categories,
		subcategoryId = transaction.subcategoryId,
		setSubcategoryId = { setTransaction(transaction.withSubcategoryId(it)) }
	)
	TransactionDate(
		date = transaction.date,
		setDate = { setTransaction(transaction.withDate(it)) }
	)
}

@Composable
private fun TransactionDetailsIncome(transaction: Income,
                                     setTransaction: (TransactionEditViewData) -> Unit,
                                     categories: List<CategoryWithSubcategoriesItemViewData>)
{
	TransactionAmount(
		initialValue = transaction.amount,
		setAmount = { setTransaction(transaction.withAmount(it)) },
		transactionType = transaction.type
	)
	TransactionSubcategory(
		categories = categories,
		subcategoryId = transaction.subcategoryId,
		setSubcategoryId = { setTransaction(transaction.withSubcategoryId(it)) }
	)
	TransactionDate(
		date = transaction.date,
		setDate = { setTransaction(transaction.withDate(it)) }
	)
}

@Composable
private fun TransactionAmount(initialValue: Double,
                              setAmount: (Double) -> Unit,
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
			it.toDoubleOrNull()?.let(setAmount)
		},
		modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 24.dp),
		textStyle = MaterialTheme.typography.h4.copy(
			color = textColor
		),
		label = {
			Text(text = stringResource(R.string.text_transaction_edit_amount))
		},
		leadingIcon = {
			Icon(Icons.Filled.Toll)
		}
	)
}

@Composable
private fun TransactionSubcategory(categories: List<CategoryWithSubcategoriesItemViewData>,
                                   subcategoryId: String,
                                   setSubcategoryId: (String) -> Unit)
{
	ExposedDropdownMenu(
		selectedValue = categories.flatMap { it.subcategories }.find { it.id == subcategoryId }?.name ?: "",
		modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
		textFieldModifier = Modifier.fillMaxWidth(),
		label = {
			Text(text = stringResource(R.string.text_transaction_edit_subcategory))
		},
		content = {
			categories.forEach { (category, subcategories) ->
				custom {
					Text(text = category.name)
				}
				subcategories.forEach { subcategory ->
					item(
						onClick = { closeDrawer ->
							setSubcategoryId(subcategory.id)
							closeDrawer()
						},
						content = {
							Text(text = subcategory.name)
						}
					)
				}
			}
		}
	)
}

@Composable
private fun TransactionDate(date: LocalDate,
                            setDate: (LocalDate) -> Unit)
{
	val dateDialog = MaterialDialog()

	dateDialog.build {
		datepicker(
			initialDate = date,
			onComplete = setDate
		)
	}

	Row(
		modifier = Modifier
				.fillMaxWidth()
				.clickable(onClick = { dateDialog.show() })
				.padding(horizontal = 24.dp, vertical = 16.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(Icons.Filled.CalendarToday)
		Text(
			modifier = Modifier.padding(start = 16.dp),
			text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
		)
	}
}

@Composable
private fun TransactionRemoveDialog(onConfirm: () -> Unit,
                                    onDismiss: () -> Unit)
{
	SimpleAlertDialog(
		title = stringResource(R.string.dialog_transaction_remove_title),
		confirmText = stringResource(R.string.text_dialog_remove),
		dismissText = stringResource(R.string.text_dialog_cancel),
		onConfirm = onConfirm,
		onDismiss = onDismiss
	)
}
