package pl.karol202.smartwallet.ui.compose.screens.accounts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.karol202.smartwallet.presentation.viewdata.AccountItemViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.Route
import pl.karol202.smartwallet.ui.compose.drawer.DrawerContent
import pl.karol202.smartwallet.ui.compose.view.AppBarIcon
import pl.karol202.smartwallet.ui.viewmodel.AndroidAccountsViewModel

@Composable
fun AccountsScreen(accountsViewModel: AndroidAccountsViewModel,
                   scaffoldState: ScaffoldState,
                   onRouteSelect: (Route.TopLevel) -> Unit,
                   onAccountCreate: () -> Unit,
                   onAccountEdit: (String) -> Unit)
{
	val allAccounts by accountsViewModel.allAccounts.collectAsState(emptyList())

	Scaffold(
		scaffoldState = scaffoldState,
		topBar = {
			TopAppBar(
				title = {
					Text(text = stringResource(R.string.screen_accounts))
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
				onClick = { onAccountCreate() },
				content = {
					Icon(Icons.Default.Add)
				}
			)
		},
		bodyContent = {
			AccountsScreenContent(
				accounts = allAccounts,
				onAccountEdit = onAccountEdit
			)
		},
	)
}

@Composable
private fun AccountsScreenContent(accounts: List<AccountItemViewData>,
                                  onAccountEdit: (String) -> Unit)
{
	AccountsList(
		accounts = accounts,
		onAccountEdit = onAccountEdit,
	)
}

@Composable
private fun AccountsList(accounts: List<AccountItemViewData>,
                         onAccountEdit: (String) -> Unit)
{
	LazyColumn {
		items(items = accounts) { account ->
			AccountItem(
				account = account,
				onAccountEdit = { onAccountEdit(account.id) }
			)
		}
	}
}

@Composable
private fun AccountItem(account: AccountItemViewData,
                        onAccountEdit: () -> Unit)
{
	Column {
		Row(
			modifier = Modifier
					.fillMaxWidth()
					.preferredHeight(48.dp)
					.clickable(onClick = onAccountEdit)
					.padding(start = 24.dp),
			verticalAlignment = Alignment.CenterVertically,
			content = {
				Text(text = account.name)
			}
		)
	}
}
