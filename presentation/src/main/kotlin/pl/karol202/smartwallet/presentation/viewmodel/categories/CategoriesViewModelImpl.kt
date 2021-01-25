package pl.karol202.smartwallet.presentation.viewmodel.categories

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.interactors.filter.noFilters
import pl.karol202.smartwallet.interactors.usecases.category.CategoryWithSubcategories
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesWithSubcategoriesFlowUseCase
import pl.karol202.smartwallet.presentation.viewdata.toItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class CategoriesViewModelImpl(getCategoriesWithSubcategoriesFlowUseCase: GetCategoriesWithSubcategoriesFlowUseCase) :
		BaseViewModel(), CategoriesViewModel
{
	override val allCategories = getCategoriesWithSubcategoriesFlowUseCase(noFilters())
			.map { it.map(CategoryWithSubcategories::toItemViewData) }
}
