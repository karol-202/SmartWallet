package pl.karol202.smartwallet.ui.compose.screens.categoryedit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import pl.karol202.smartwallet.presentation.viewdata.CategoryEditViewData
import pl.karol202.smartwallet.presentation.viewdata.CategoryTypeViewData
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryItemViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.view.AppBarIcon
import pl.karol202.smartwallet.ui.compose.view.RadioButtonWithText
import pl.karol202.smartwallet.ui.compose.view.SimpleAlertDialog
import pl.karol202.smartwallet.ui.compose.view.ToggleButtonGroup
import pl.karol202.smartwallet.ui.viewmodel.AndroidCategoryEditViewModel
import java.util.*

@Composable
fun CategoryEditScreen(categoryEditViewModel: AndroidCategoryEditViewModel,
                       categoryId: String?,
                       onNavigateBack: () -> Unit,
                       onSubcategoryCreate: (categoryId: String) -> Unit,
                       onSubcategoryEdit: (subcategoryId: String) -> Unit)
{
	LaunchedEffect(categoryEditViewModel, categoryId) {
		if(categoryId == null) categoryEditViewModel.editNewCategory()
		else categoryEditViewModel.editExistingCategory(categoryId)
	}
	LaunchedEffect(categoryEditViewModel) {
		categoryEditViewModel.finishEvent.collect { onNavigateBack() }
	}

	val editedCategory = categoryEditViewModel.editedCategory.collectAsState(null).value ?: return
	val subcategories = categoryEditViewModel.subcategories.collectAsState(null).value

	var removeDialogVisible by remember { mutableStateOf(false) }

	Scaffold(
		topBar = {
			CategoryEditScreenAppbar(
				categoryExists = categoryId != null,
				onNavigateBack = onNavigateBack,
				onRemove = { removeDialogVisible = true }
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					categoryEditViewModel.apply()
				},
				content = {
					Icon(Icons.Filled.Done)
				}
			)
		},
		bodyContent = {
			CategoryEditScreenContent(
				category = editedCategory,
				setCategory = { categoryEditViewModel.setCategory(it) },
				subcategories = subcategories,
				onSubcategoryCreate = { if(categoryId != null) onSubcategoryCreate(categoryId) },
				onSubcategoryEdit = onSubcategoryEdit
			)
		},
	)

	if(removeDialogVisible) CategoryRemoveDialog(
		categoryName = editedCategory.name,
		onConfirm = { categoryEditViewModel.removeCategory() },
		onDismiss = { removeDialogVisible = false }
	)
}

@Composable
private fun CategoryEditScreenAppbar(categoryExists: Boolean,
                                     onNavigateBack: () -> Unit,
                                     onRemove: () -> Unit)
{
	TopAppBar(
		title = {
			Text(
				text = stringResource(if(categoryExists) R.string.screen_category_edit
				                      else R.string.screen_category_new)
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
				enabled = categoryExists,
				onClick = onRemove
			)
		}
	)
}

@Composable
private fun CategoryEditScreenContent(category: CategoryEditViewData,
                                      setCategory: (CategoryEditViewData) -> Unit,
                                      subcategories: List<SubcategoryItemViewData>?,
                                      onSubcategoryCreate: () -> Unit,
                                      onSubcategoryEdit: (String) -> Unit)
{
	Column {
		CategoryTypeSelector(
			type = category.type,
			setType = { setCategory(category.withType(it)) }
		)
		CategoryName(
			name = category.name,
			setName = { setCategory(category.withName(it)) }
		)
		if(subcategories != null) CategorySubcategories(
			subcategories = subcategories,
			onSubcategoryCreate = onSubcategoryCreate,
			onSubcategoryEdit = onSubcategoryEdit
		)
	}
}

@Composable
private fun CategoryTypeSelector(type: CategoryTypeViewData,
                                 setType: (CategoryTypeViewData) -> Unit)
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
						checked = type == CategoryTypeViewData.EXPENSE,
						onClick = { setType(CategoryTypeViewData.EXPENSE) },
						content = {
							Text(text = stringResource(R.string.category_type_expense))
						}
					)
					item(
						checked = type == CategoryTypeViewData.INCOME,
						onClick = { setType(CategoryTypeViewData.INCOME) },
						content = {
							Text(text = stringResource(R.string.category_type_income))
						}
					)
				}
			)
		}
	)
}

@Composable
private fun CategoryName(name: String,
                         setName: (String) -> Unit)
{
	TextField(
		value = name,
		onValueChange = setName,
		modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 24.dp),
		label = {
			Text(text = stringResource(R.string.text_category_edit_name))
		}
	)
}

@Composable
private fun CategorySubcategories(subcategories: List<SubcategoryItemViewData>,
                                  onSubcategoryCreate: () -> Unit,
                                  onSubcategoryEdit: (String) -> Unit)
{
	Column {
		Row(
			modifier = Modifier
					.fillMaxWidth()
					.padding(horizontal = 24.dp, vertical = 8.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = stringResource(R.string.text_category_edit_subcategories).toUpperCase(Locale.ROOT),
				style = MaterialTheme.typography.overline,
				color = MaterialTheme.colors.primaryVariant,
				fontWeight = FontWeight.Bold
			)
			IconButton(
				onClick = onSubcategoryCreate,
				content = {
					Icon(Icons.Filled.Add)
				}
			)
		}
		LazyColumn {
			items(subcategories) {
				CategorySubcategoryItem(
					subcategory = it,
					onEdit = { onSubcategoryEdit(it.id) }
				)
			}
		}
	}
}

@Composable
private fun CategorySubcategoryItem(subcategory: SubcategoryItemViewData,
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
		Text(text = subcategory.name)
		IconButton(
			onClick = onEdit,
			content = {
				Icon(Icons.Filled.Edit)
			}
		)
	}
}

@Composable
private fun CategoryRemoveDialog(categoryName: String,
                                 onConfirm: () -> Unit,
                                 onDismiss: () -> Unit)
{
	SimpleAlertDialog(
		title = stringResource(R.string.dialog_category_remove_title, categoryName),
		confirmText = stringResource(R.string.text_dialog_remove),
		dismissText = stringResource(R.string.text_dialog_cancel),
		onConfirm = onConfirm,
		onDismiss = onDismiss
	)
}
