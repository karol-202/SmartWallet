package pl.karol202.smartwallet.interactors.usecases.subcategory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.filter.FilterFunction
import pl.karol202.smartwallet.interactors.filter.FilterScope
import pl.karol202.smartwallet.interactors.filter.filterWith
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class GetSubcategoriesFlowUseCase(
		override val function: suspend (FilterFunction<Subcategory<Existing>>) -> Flow<List<Subcategory<Existing>>>
) : SuspendUseCase1<FilterFunction<Subcategory<Existing>>, Flow<List<Subcategory<Existing>>>>

fun getSubcategoriesFlowUseCaseFactory(subcategoryRepository: SubcategoryRepository) = GetSubcategoriesFlowUseCase { filter ->
	subcategoryRepository.allSubcategories.map { subcategories ->
		subcategories.filterWith(filter)
	}
}

fun FilterScope<Subcategory<Existing>>.filterByCategoryId(categoryId: String) = filter { it.categoryId == categoryId }
