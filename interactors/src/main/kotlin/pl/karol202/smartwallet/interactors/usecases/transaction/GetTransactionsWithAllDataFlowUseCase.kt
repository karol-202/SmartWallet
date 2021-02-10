package pl.karol202.smartwallet.interactors.usecases.transaction

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import pl.karol202.smartwallet.domain.entity.*
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.filter.noFilters
import pl.karol202.smartwallet.interactors.usecases.UseCase0
import pl.karol202.smartwallet.interactors.usecases.category.GetCategoriesWithSubcategoriesFlowUseCase

data class TransactionWithAllData(val transaction: Transaction<Existing>,
                                  val category: Category<Existing>,
                                  val subcategory: Subcategory<Existing>,
                                  val account: Account<Existing>)

class GetTransactionsWithAllDataFlowUseCase(override val function: () -> Flow<List<TransactionWithAllData>>) :
		UseCase0<Flow<List<TransactionWithAllData>>>

fun getTransactionsWithCategoriesFlowUseCaseFactory(
		transactionRepository: TransactionRepository,
		getCategoriesWithSubcategoriesFlowUseCase: GetCategoriesWithSubcategoriesFlowUseCase,
		accountRepository: AccountRepository,
) = GetTransactionsWithAllDataFlowUseCase {
	combine(transactionRepository.allTransactions,
	        getCategoriesWithSubcategoriesFlowUseCase(noFilters()),
	        accountRepository.allAccounts) { transactions, categoriesMap, accounts ->
		val categoriesAndSubcategories = categoriesMap.flatMap { (category, subcategories) ->
			subcategories.map { category to it }
		}

		transactions.map { transaction -> constructTransactionData(transaction, categoriesAndSubcategories, accounts) }
	}
}

private fun constructTransactionData(transaction: Transaction<Existing>,
                                     categoriesAndSubcategories: List<Pair<Category<Existing>, Subcategory<Existing>>>,
                                     accounts: List<Account<Existing>>): TransactionWithAllData
{
	val (category, subcategory) = categoriesAndSubcategories.findSubcategoryById(transaction.subcategoryId)

	return when(transaction)
	{
		is Transaction.Expense -> {
			val account = accounts.findAccountById(transaction.accountId)
			TransactionWithAllData(transaction, category, subcategory, account)
		}
		is Transaction.Income -> {
			val account = accounts.findAccountById(transaction.accountId)
			TransactionWithAllData(transaction, category, subcategory, account)
		}
	}
}

private fun List<Pair<Category<Existing>, Subcategory<Existing>>>.findSubcategoryById(subcategoryId: String) =
		find { (_, subcategory) -> subcategory.id.value == subcategoryId } ?: error("Subcategory does not exist")

private fun List<Account<Existing>>.findAccountById(accountId: String) =
		find { it.id.value == accountId } ?: error("Account does not exist")
