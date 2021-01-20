package pl.karol202.smartwallet.ui.compose.screens.categoryedit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Toll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collect
import pl.karol202.smartwallet.presentation.viewdata.CategoryEditViewData
import pl.karol202.smartwallet.presentation.viewdata.CategoryTypeViewData
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryItemViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.view.RadioButtonWithText
import pl.karol202.smartwallet.ui.viewmodel.AndroidCategoryEditViewModel
import java.util.*

@Composable
fun CategoryEditScreen(categoryEditViewModel: AndroidCategoryEditViewModel,
                       categoryId: String?,
                       onNavigateBack: () -> Unit,
                       onSubcategoryCreate: (categoryId: String) -> Unit,
                       onSubcategoryEdit: (categoryId: String, subcategoryId: String) -> Unit)
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
				setCategory = { categoryEditViewModel.setCategory(it) },
				subcategories = subcategories,
				onSubcategoryCreate = { if(categoryId != null) onSubcategoryCreate(categoryId) },
				onSubcategoryEdit = { if(categoryId != null) onSubcategoryEdit(categoryId, it) }
			)
		},
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
