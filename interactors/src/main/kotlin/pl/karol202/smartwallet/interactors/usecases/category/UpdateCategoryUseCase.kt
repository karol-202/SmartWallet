package pl.karol202.smartwallet.interactors.usecases.category

import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class UpdateCategoryUseCase(override val function: suspend (Category<Existing>) -> Unit) :
		SuspendUseCase1<Category<Existing>, Unit>

fun updateCategoryUseCaseFactory(categoryRepository: CategoryRepository) = UpdateCategoryUseCase { category ->
	categoryRepository.updateCategory(category)
}
