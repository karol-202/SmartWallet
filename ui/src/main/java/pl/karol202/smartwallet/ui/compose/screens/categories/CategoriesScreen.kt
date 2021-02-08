package pl.karol202.smartwallet.ui.compose.screens.categories

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
import pl.karol202.smartwallet.presentation.viewdata.CategoryItemViewData
import pl.karol202.smartwallet.presentation.viewdata.CategoryWithSubcategoriesItemViewData
import pl.karol202.smartwallet.presentation.viewdata.SubcategoryItemViewData
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.compose.Route
import pl.karol202.smartwallet.ui.compose.drawer.DrawerContent
import pl.karol202.smartwallet.ui.compose.view.AppBarIcon
import pl.karol202.smartwallet.ui.viewmodel.AndroidCategoriesViewModel

@Composable
fun CategoriesScreen(categoriesViewModel: AndroidCategoriesViewModel,
                     scaffoldState: ScaffoldState,
                     onRouteSelect: (Route.TopLevel) -> Unit,
                     onCategoryCreate: () -> Unit,
                     onCategoryEdit: (String) -> Unit,
                     onSubcategoryEdit: (String) -> Unit)
{
	val allCategories by categoriesViewModel.allCategories.collectAsState(emptyList())

	Scaffold(
		scaffoldState = scaffoldState,
		topBar = {
			TopAppBar(
				title = {
					Text(text = stringResource(R.string.screen_categories))
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
				onClick = { onCategoryCreate() },
				content = {
					Icon(Icons.Default.Add)
				}
			)
		},
		bodyContent = {
			CategoriesScreenContent(
				categories = allCategories,
				onCategoryEdit = onCategoryEdit,
				onSubcategoryEdit = onSubcategoryEdit
			)
		},
	)
}

@Composable
private fun CategoriesScreenContent(categories: List<CategoryWithSubcategoriesItemViewData>,
                                    onCategoryEdit: (String) -> Unit,
                                    onSubcategoryEdit: (String) -> Unit)
{
	CategoriesList(
		categories = categories,
		onCategoryEdit = onCategoryEdit,
		onSubcategoryEdit = onSubcategoryEdit
	)
}

@Composable
private fun CategoriesList(categories: List<CategoryWithSubcategoriesItemViewData>,
                           onCategoryEdit: (String) -> Unit,
                           onSubcategoryEdit: (String) -> Unit)
{
	LazyColumn {
		items(items = categories) { (category, subcategories) ->
			CategoryItem(
				category = category,
				subcategories = subcategories,
				onCategoryEdit = { onCategoryEdit(category.id) },
				onSubcategoryEdit = onSubcategoryEdit
			)
		}
	}
}

@Composable
private fun CategoryItem(category: CategoryItemViewData,
                         subcategories: List<SubcategoryItemViewData>,
                         onCategoryEdit: () -> Unit,
                         onSubcategoryEdit: (String) -> Unit)
{
	Column {
		Row(
			modifier = Modifier
					.fillMaxWidth()
					.preferredHeight(48.dp)
					.clickable(onClick = onCategoryEdit)
					.padding(start = 24.dp),
			verticalAlignment = Alignment.CenterVertically,
			content = {
				Text(text = category.name)
			}
		)
		subcategories.forEach { subcategory ->
			SubcategoryItem(
				subcategory = subcategory,
				onEdit = { onSubcategoryEdit(subcategory.id) }
			)
		}
	}
}

@Composable
private fun SubcategoryItem(subcategory: SubcategoryItemViewData,
                            onEdit: () -> Unit)
{
	Row(
		modifier = Modifier
				.fillMaxWidth()
				.preferredHeight(48.dp)
				.clickable(onClick = onEdit)
				.padding(start = 36.dp),
		verticalAlignment = Alignment.CenterVertically,
		content = {
			Text(text = subcategory.name)
		}
	)
}
