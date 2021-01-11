package pl.karol202.smartwallet.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.NewTransaction
import pl.karol202.smartwallet.domain.entity.Transaction

interface TransactionRepository
{
	val allTransactions: Flow<List<Transaction>>

	suspend fun addTransaction(transaction: NewTransaction)

	suspend fun updateTransaction(transaction: Transaction)

	suspend fun removeTransaction(transactionId: Long)
}
