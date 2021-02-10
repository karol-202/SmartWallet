package pl.karol202.smartwallet.interactors.usecases.subcategory

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase2
import pl.karol202.smartwallet.interactors.usecases.subcategory.RemoveSubcategoryUseCase.TransactionsPolicy

class RemoveSubcategoryUseCase(override val function: suspend (String, TransactionsPolicy) -> Unit) :
		SuspendUseCase2<String, TransactionsPolicy, Unit>
{
	enum class TransactionsPolicy
	{
		REMOVE, MOVE_TO_OTHERS
	}
}

fun removeSubcategoryUseCaseFactory(categoryRepository: CategoryRepository,
                                    subcategoryRepository: SubcategoryRepository,
                                    transactionRepository: TransactionRepository) = RemoveSubcategoryUseCase { id, policy ->
	when(policy)
	{
		TransactionsPolicy.MOVE_TO_OTHERS ->
			moveTransactionsToOthers(categoryRepository, subcategoryRepository, transactionRepository, id)
		TransactionsPolicy.REMOVE ->
			transactionRepository.removeTransactionsOfSubcategory(id)
	}
	subcategoryRepository.removeSubcategory(id)
}

private suspend fun moveTransactionsToOthers(categoryRepository: CategoryRepository,
                                             subcategoryRepository: SubcategoryRepository,
                                             transactionRepository: TransactionRepository,
                                             sourceId: String)
{
	val source = subcategoryRepository.getSubcategory(sourceId).first() ?: error("Subcategory does not exist")
	val sourceParent = categoryRepository.getCategory(source.categoryId).first() ?: error("Category does not exist")
	val destinationId = subcategoryRepository.getOthersSubcategoryId(sourceParent.type)
	transactionRepository.moveTransactionsBetweenSubcategories(sourceId, destinationId)
}
