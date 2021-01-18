package pl.karol202.smartwallet.ui.compose.screens.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.karol202.smartwallet.presentation.viewdata.CategoryItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.CategoriesViewModel
import pl.karol202.smartwallet.ui.R
import pl.karol202.smartwallet.ui.viewmodel.AndroidCategoriesViewModel

@Composable
fun CategoriesScreen(categoriesViewModel: AndroidCategoriesViewModel,
                     scaffoldState: ScaffoldState)
{
	val allCategories by categoriesViewModel.allCategories.collectAsState()

	Scaffold(
		scaffoldState = scaffoldState,
		topBar = {
			TopAppBar(
				title = {
					Text(text = stringResource(R.string.screen_categories))
				}
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = { },
				content = {
					Icon(Icons.Default.Add)
				}
			)
		},
		bodyContent = {
			CategoriesScreenContent(
				categories = allCategories
			)
		},
	)
}

@Composable
private fun CategoriesScreenContent(categories: List<CategoryItemViewData>)
{
	CategoriesList(categories = categories)
}

@Composable
fun CategoriesList(categories: List<CategoryItemViewData>)
{
	LazyColumn {
		items(items = categories) { category ->
			CategoryItem(category = category)
		}
	}
}

@Composable
fun CategoryItem(category: CategoryItemViewData)
{
	Row(
		modifier = Modifier
				.fillMaxWidth()
				.clickable(onClick = {})
				.padding(start = 24.dp, end = 8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(text = category.name)
	}
}
