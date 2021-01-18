package pl.karol202.smartwallet.interactors.usecases.category

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class GetCategoryUseCase(override val function: suspend (String) -> Category<Existing>) :
		SuspendUseCase1<String, Category<Existing>>

fun getCategoryUseCaseFactory(categoryRepository: CategoryRepository) = GetCategoryUseCase { id ->
	categoryRepository.getCategory(id).first()
}
