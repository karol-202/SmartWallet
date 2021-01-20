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
import pl.karol202.smartwallet.presentation.viewdata.CategoryItemViewData
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryEditViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.view.AppBarIcon
import pl.karol202.smartwallet.ui.compose.view.ExposedDropdownMenu
import pl.karol202.smartwallet.ui.compose.view.SimpleAlertDialog
import pl.karol202.smartwallet.ui.viewmodel.AndroidSubcategoryEditViewModel

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

	val allCategories by subcategoryEditViewModel.allCategories.collectAsState(emptyList())
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
				categories = allCategories,
				subcategory = editedSubcategory,
				setSubcategory = { subcategoryEditViewModel.setSubcategory(it) },
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
private fun SubcategoryEditScreenContent(categories: List<CategoryItemViewData>,
                                         subcategory: SubcategoryEditViewData,
                                         setSubcategory: (SubcategoryEditViewData) -> Unit)
{
	Column {
		SubcategoryName(
			name = subcategory.name,
			setName = { setSubcategory(subcategory.withName(it)) }
		)
		SubcategoryCategory(
			categories = categories,
			categoryId = subcategory.categoryId,
			setCategory = { setSubcategory(subcategory.withCategory(it)) }
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
				.padding(horizontal = 24.dp, vertical = 8.dp),
		label = {
			Text(text = stringResource(R.string.text_subcategory_edit_name))
		}
	)
}

@Composable
private fun SubcategoryCategory(categories: List<CategoryItemViewData>,
                                categoryId: String,
                                setCategory: (String) -> Unit)
{
	val (isOpen, setOpen) = remember { mutableStateOf(false) }

	ExposedDropdownMenu(
		selectedValue = categories.find { it.id == categoryId }?.name ?: "",
		isOpen = isOpen,
		onOpenChange = setOpen,
		modifier = Modifier
				.padding(horizontal = 24.dp, vertical = 8.dp),
		textFieldModifier = Modifier
				.fillMaxWidth(),
		label = {
			Text(text = stringResource(R.string.text_subcategory_edit_category))
		},
		content = {
			categories.forEach {
				DropdownMenuItem(
					onClick = {
						setCategory(it.id)
						setOpen(false)
					},
					content = {
						Text(text = it.name)
					}
				)
			}
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
		confirmText = stringResource(R.string.text_dialog_remove),
		dismissText = stringResource(R.string.text_dialog_cancel),
		onConfirm = onConfirm,
		onDismiss = onDismiss
	)
}
