package pl.karol202.smartwallet.interactors.usecases.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.interactors.filter.FilterFunction
import pl.karol202.smartwallet.interactors.filter.FilterScope
import pl.karol202.smartwallet.interactors.filter.filterWith
import pl.karol202.smartwallet.interactors.usecases.UseCase1

class GetCategoriesFlowUseCase(
		override val function: (FilterFunction<Category<Existing>>) -> Flow<List<Category<Existing>>>
) : UseCase1<FilterFunction<Category<Existing>>, Flow<List<Category<Existing>>>>

fun getCategoriesFlowUseCaseFactory(categoryRepository: CategoryRepository) = GetCategoriesFlowUseCase { filter ->
	categoryRepository.allCategories.map { categories ->
		categories.filterWith(filter)
	}
}

fun FilterScope<Category<Existing>>.filterByType(type: Category.Type) = filter { it.type == type }
