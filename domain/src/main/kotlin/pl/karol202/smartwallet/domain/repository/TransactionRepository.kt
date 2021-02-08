package pl.karol202.smartwallet.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Transaction

interface TransactionRepository
{
	val allTransactions: Flow<List<Transaction<Existing>>>

	fun getTransaction(transactionId: String): Flow<Transaction<Existing>?>

	suspend fun addTransaction(transaction: Transaction<New>)

	suspend fun updateTransaction(transaction: Transaction<Existing>)

	suspend fun removeTransaction(transactionId: String)

	suspend fun moveTransactionsBetweenSubcategories(sourceSubcategoryId: String, destinationSubcategoryId: String)
}
