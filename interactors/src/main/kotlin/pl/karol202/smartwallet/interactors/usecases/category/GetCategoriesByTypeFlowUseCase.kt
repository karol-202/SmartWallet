package pl.karol202.smartwallet.interactors.usecases.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.interactors.usecases.UseCase1

// Providing null category type as an argument means that no filer will be applied
class GetCategoriesByTypeFlowUseCase(override val function: (Category.Type?) -> Flow<List<Category<Existing>>>) :
		UseCase1<Category.Type?, Flow<List<Category<Existing>>>>

fun getCategoriesFlowUseCaseFactory(categoryRepository: CategoryRepository) = GetCategoriesByTypeFlowUseCase { filterType ->
	categoryRepository.allCategories.map { categories ->
		if(filterType == null) categories
		else categories.filter { it.type == filterType }
	}
}
