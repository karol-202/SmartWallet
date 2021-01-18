package pl.karol202.smartwallet.ui.compose.screens.categoryedit

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Toll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import pl.karol202.smartwallet.presentation.viewdata.CategoryEditViewData
import pl.karol202.smartwallet.presentation.viewdata.CategoryTypeViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.view.RadioButtonWithText
import pl.karol202.smartwallet.ui.viewmodel.AndroidCategoryEditViewModel

@Composable
fun CategoryEditScreen(categoryEditViewModel: AndroidCategoryEditViewModel,
                       categoryId: String?,
                       onNavigateBack: () -> Unit)
{
	LaunchedEffect(categoryEditViewModel, categoryId) {
		if(categoryId == null) categoryEditViewModel.editNewCategory()
		else categoryEditViewModel.editExistingCategory(categoryId)
	}
	LaunchedEffect(categoryEditViewModel) {
		categoryEditViewModel.finishEvent.collect { onNavigateBack() }
	}

	val editedCategory = categoryEditViewModel.editedCategory.collectAsState(null).value ?: return

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = stringResource(if(categoryId == null) R.string.screen_category_new
						                      else R.string.screen_category_edit)
					)
				}
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
				setCategory = { categoryEditViewModel.setCategory(it) }
			)
		},
	)
}

@Composable
private fun CategoryEditScreenContent(category: CategoryEditViewData,
                                      setCategory: (CategoryEditViewData) -> Unit)
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
	}
}

@Composable
private fun CategoryTypeSelector(type: CategoryTypeViewData,
                                 setType: (CategoryTypeViewData) -> Unit)
{
	Row(
		modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		RadioButtonWithText(
			text = stringResource(R.string.category_type_expense),
			selected = type == CategoryTypeViewData.EXPENSE,
			onClick = { setType(CategoryTypeViewData.EXPENSE) }
		)
		RadioButtonWithText(
			text = stringResource(R.string.category_type_income),
			selected = type == CategoryTypeViewData.INCOME,
			onClick = { setType(CategoryTypeViewData.INCOME) }
		)
	}
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
