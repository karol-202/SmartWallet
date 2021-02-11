package pl.karol202.smartwallet.ui.compose.screens.accountedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import pl.karol202.smartwallet.presentation.viewdata.AccountEditViewData
import pl.karol202.smartwallet.presentation.viewmodel.accountsedit.AccountEditViewModel.RemovalCapability
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.view.AppBarIcon
import pl.karol202.smartwallet.ui.compose.view.SimpleAlertButtonsOrientation
import pl.karol202.smartwallet.ui.compose.view.SimpleAlertDialog
import pl.karol202.smartwallet.ui.viewmodel.AndroidAccountEditViewModel

@Composable
fun AccountEditScreen(accountEditViewModel: AndroidAccountEditViewModel,
                      accountId: String?,
                      onNavigateBack: () -> Unit)
{
	LaunchedEffect(accountEditViewModel, accountId) {
		if(accountId == null) accountEditViewModel.editNewAccount()
		else accountEditViewModel.editExistingAccount(accountId)
	}
	LaunchedEffect(accountEditViewModel) {
		accountEditViewModel.finishEvent.collect { onNavigateBack() }
	}

	val editedAccount = accountEditViewModel.editedAccount.collectAsState(null).value ?: return
	val removalCapability by accountEditViewModel.removalCapability.collectAsState(RemovalCapability.REMOVABLE)

	var removalDialogVisible by remember { mutableStateOf(false) }
	var removalImpossibleDialogVisible by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			AccountEditScreenAppbar(
				accountExists = accountId != null,
				onNavigateBack = onNavigateBack,
				onRemove = {
					if(removalCapability == RemovalCapability.REMOVABLE) removalDialogVisible = true
					else removalImpossibleDialogVisible = true
				}
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					accountEditViewModel.apply()
				},
				content = {
					Icon(Icons.Filled.Done)
				}
			)
		},
		bodyContent = {
			AccountEditScreenContent(
				account = editedAccount,
				setAccount = { accountEditViewModel.setAccount(it) }
			)
		},
	)

	if(removalDialogVisible) AccountRemovalDialog(
		accountName = editedAccount.name,
		onConfirm = { accountEditViewModel.removeAccount() },
		onDismiss = { removalDialogVisible = false }
	)
	if(removalImpossibleDialogVisible) AccountRemovalImpossibleDialog(
		removalCapability = removalCapability,
		onDismiss = { removalImpossibleDialogVisible = false }
	)
}

@Composable
private fun AccountEditScreenAppbar(accountExists: Boolean,
                                    onNavigateBack: () -> Unit,
                                    onRemove: () -> Unit)
{
	TopAppBar(
		title = {
			Text(
				text = stringResource(if(accountExists) R.string.screen_account_edit
				                      else R.string.screen_account_new)
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
				enabled = accountExists,
				onClick = onRemove
			)
		}
	)
}

@Composable
private fun AccountEditScreenContent(account: AccountEditViewData,
                                     setAccount: (AccountEditViewData) -> Unit)
{
	Column {
		AccountName(
			name = account.name,
			setName = { setAccount(account.withName(it)) }
		)
	}
}

@Composable
private fun AccountName(name: String,
                        setName: (String) -> Unit)
{
	TextField(
		value = name,
		onValueChange = setName,
		modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 24.dp, vertical = 16.dp),
		label = {
			Text(text = stringResource(R.string.text_account_edit_name))
		}
	)
}

@Composable
private fun AccountRemovalDialog(accountName: String,
                                 onConfirm: () -> Unit,
                                 onDismiss: () -> Unit)
{
	SimpleAlertDialog(
		title = stringResource(R.string.dialog_account_remove_title, accountName),
		buttonsOrientation = SimpleAlertButtonsOrientation.VERTICAL,
		buttons = {
			button(R.string.text_account_remove_dialog_remove, onConfirm)
			dismissButton(R.string.text_dialog_cancel, onDismiss)
		}
	)
}

@Composable
private fun AccountRemovalImpossibleDialog(removalCapability: RemovalCapability,
                                           onDismiss: () -> Unit)
{
	SimpleAlertDialog(
		title = when(removalCapability)
		{
			RemovalCapability.DEFAULT_ACCOUNT ->
				stringResource(R.string.dialog_account_remove_impossible_default_title)
			RemovalCapability.REMOVABLE -> error("Invalid impossibility cause")
		},
		buttons = {
			dismissButton(R.string.text_dialog_ok, onDismiss)
		}
	)
}
