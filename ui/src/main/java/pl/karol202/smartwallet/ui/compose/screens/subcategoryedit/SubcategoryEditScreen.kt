package pl.karol202.smartwallet.ui.compose.screens.subcategoryedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryEditViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.view.AppBarIcon
import pl.karol202.smartwallet.ui.compose.view.SimpleAlertDialog
import pl.karol202.smartwallet.ui.viewmodel.AndroidSubcategoryEditViewModel
import java.util.*

@Composable
fun SubcategoryEditScreen(subcategoryEditViewModel: AndroidSubcategoryEditViewModel,
                          categoryId: String,
                          subcategoryId: String?,
                          onNavigateBack: () -> Unit)
{
	LaunchedEffect(subcategoryEditViewModel, subcategoryId) {
		if(subcategoryId == null) subcategoryEditViewModel.editNewSubcategory(categoryId)
		else subcategoryEditViewModel.editExistingSubcategory(subcategoryId)
	}
	LaunchedEffect(subcategoryEditViewModel) {
		subcategoryEditViewModel.finishEvent.collect { onNavigateBack() }
	}

	val editedSubcategory = subcategoryEditViewModel.editedSubcategory.collectAsState(null).value ?: return

	var removeDialogVisible by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			SubcategoryEditScreenAppbar(
				subcategoryExists = subcategoryId != null,
				onRemove = { removeDialogVisible = true }
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = { subcategoryEditViewModel.apply() },
				content = {
					Icon(Icons.Filled.Done)
				}
			)
		},
		bodyContent = {
			SubcategoryEditScreenContent(
				subcategory = editedSubcategory,
				setSubcategory = { subcategoryEditViewModel.setSubcategory(it) }
			)
		},
	)

	if(removeDialogVisible) SubcategoryRemoveDialog(
		subcategoryName = editedSubcategory.name,
		onConfirm = { subcategoryEditViewModel.removeSubcategory() },
		onDismiss = { removeDialogVisible = false }
	)
}

@Composable
fun SubcategoryEditScreenAppbar(subcategoryExists: Boolean,
                                onRemove: () -> Unit)
{
	TopAppBar(
		title = {
			Text(
				text = stringResource(if(subcategoryExists) R.string.screen_subcategory_edit
				                      else R.string.screen_subcategory_new)
			)
		},
		actions = {
			AppBarIcon(
				imageVector = Icons.Filled.Delete,
				enabled = subcategoryExists,
				onClick = onRemove
			)
		}
	)
}

@Composable
private fun SubcategoryEditScreenContent(subcategory: SubcategoryEditViewData,
                                         setSubcategory: (SubcategoryEditViewData) -> Unit)
{
	Column {
		SubcategoryName(
			name = subcategory.name,
			setName = { setSubcategory(subcategory.withName(it)) }
		)
	}
}

@Composable
private fun SubcategoryName(name: String,
                            setName: (String) -> Unit)
{
	TextField(
		value = name,
		onValueChange = setName,
		modifier = Modifier
				.fillMaxWidth()
				.padding(start = 24.dp, end = 24.dp, top = 16.dp),
		label = {
			Text(text = stringResource(R.string.text_subcategory_edit_name))
		}
	)
}

@Composable
private fun SubcategoryRemoveDialog(subcategoryName: String,
                                    onConfirm: () -> Unit,
                                    onDismiss: () -> Unit)
{
	SimpleAlertDialog(
		title = stringResource(R.string.dialog_subcategory_remove_title, subcategoryName),
		confirmText = stringResource(R.string.dialog_subcategory_remove_ok),
		dismissText = stringResource(R.string.dialog_subcategory_remove_ok),
		onConfirm = onConfirm,
		onDismiss = onDismiss
	)
}
