package pl.karol202.smartwallet.interactors.usecases.category

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.interactors.usecases.UseCase0

class GetCategoriesFlowUseCase(override val function: () -> Flow<List<Category<Existing>>>) :
		UseCase0<Flow<List<Category<Existing>>>>

fun getCategoriesFlowUseCaseFactory(categoryRepository: CategoryRepository) = GetCategoriesFlowUseCase {
	categoryRepository.allCategories
}
