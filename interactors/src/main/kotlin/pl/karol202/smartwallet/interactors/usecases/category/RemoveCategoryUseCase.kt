package pl.karol202.smartwallet.interactors.usecases.category

import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class RemoveCategoryUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun removeCategoryUseCaseFactory(categoryRepository: CategoryRepository) = RemoveCategoryUseCase { id ->
	categoryRepository.removeCategory(id)
}
