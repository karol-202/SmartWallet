package pl.karol202.smartwallet.interactors.usecases.category

import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class AddCategoryUseCase(override val function: suspend (Category<New>) -> Unit) : SuspendUseCase1<Category<New>, Unit>

fun addCategoryUseCaseFactory(categoryRepository: CategoryRepository) = AddCategoryUseCase { category ->
	categoryRepository.addCategory(category)
}
