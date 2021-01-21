package pl.karol202.smartwallet.presentation.viewmodel.categories

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesFlowUseCase
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesWithSubcategoriesFlowUseCase
import pl.karol202.smartwallet.presentation.viewdata.toItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.BaseViewModel

class CategoriesViewModelImpl(getCategoriesWithSubcategoriesFlowUseCase: GetCategoriesWithSubcategoriesFlowUseCase) :
		BaseViewModel(), CategoriesViewModel
{
	override val allCategories = getCategoriesWithSubcategoriesFlowUseCase()
			.map { it.map { (category, subcategories) -> category.toItemViewData(subcategories) } }
}
