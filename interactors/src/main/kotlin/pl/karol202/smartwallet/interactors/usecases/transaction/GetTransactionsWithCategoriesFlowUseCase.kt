package pl.karol202.smartwallet.interactors.usecases.transaction

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.filter.noFilters
import pl.karol202.smartwallet.interactors.usecases.UseCase0
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesWithSubcategoriesFlowUseCase

data class TransactionWithCategoryData(val transaction: Transaction<Existing>,
                                       val category: Category<Existing>,
                                       val subcategory: Subcategory<Existing>)

class GetTransactionsWithCategoriesFlowUseCase(override val function: () -> Flow<List<TransactionWithCategoryData>>) :
		UseCase0<Flow<List<TransactionWithCategoryData>>>

fun getTransactionsWithCategoriesFlowUseCaseFactory(
		transactionRepository: TransactionRepository,
		getCategoriesWithSubcategoriesFlowUseCase: GetCategoriesWithSubcategoriesFlowUseCase
) = GetTransactionsWithCategoriesFlowUseCase {
	transactionRepository.allTransactions.combine(getCategoriesWithSubcategoriesFlowUseCase(noFilters())) { transactions, categoriesMap ->
		transactions.map { transaction ->
			val (category, subcategory) = categoriesMap
					.flatMap { (category, subcategories) -> subcategories.map { category to it } }
					.find { (_, subcategory) -> transaction.subcategoryId == subcategory.id.value }
					?: error("Subcategory does not exist")
			TransactionWithCategoryData(transaction, category, subcategory)
		}
	}
}
