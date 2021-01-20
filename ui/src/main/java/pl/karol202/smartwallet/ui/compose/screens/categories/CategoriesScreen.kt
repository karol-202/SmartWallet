package pl.karol202.smartwallet.ui.compose.screens.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.karol202.smartwallet.presentation.viewdata.CategoryItemViewData
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
                     onCategoryEdit: (String) -> Unit)
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
				onEdit = onCategoryEdit
			)
		},
	)
}

@Composable
private fun CategoriesScreenContent(categories: List<CategoryItemViewData>,
                                    onEdit: (String) -> Unit)
{
	CategoriesList(categories = categories,
	               onEdit = onEdit)
}

@Composable
fun CategoriesList(categories: List<CategoryItemViewData>,
                   onEdit: (String) -> Unit)
{
	LazyColumn {
		items(items = categories) { category ->
			CategoryItem(category = category,
			             onEdit = { onEdit(category.id) })
		}
	}
}

@Composable
fun CategoryItem(category: CategoryItemViewData,
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
		Text(text = category.name)
		IconButton(
			onClick = onEdit,
			content = {
				Icon(Icons.Filled.Edit)
			}
		)
	}
}
