package pl.karol202.smartwallet.presentation.viewmodel.impl

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesFlowUseCase
import pl.karol202.smartwallet.presentation.viewdata.toItemViewData
import pl.karol202.smartwallet.presentation.viewmodel.CategoriesViewModel

class CategoriesViewModelImpl(getCategoriesFlowUseCase: GetCategoriesFlowUseCase) :
		BaseViewModel(), CategoriesViewModel
{
	override val allCategories = getCategoriesFlowUseCase()
			.map { it.map(Category<Existing>::toItemViewData) }
			.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}
