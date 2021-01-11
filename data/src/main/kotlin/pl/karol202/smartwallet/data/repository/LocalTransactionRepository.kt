package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.TransactionDataSource
import pl.karol202.smartwallet.data.model.TransactionModel
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.domain.entity.NewTransaction
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository

class LocalTransactionRepository(private val transactionDataSource: TransactionDataSource) : TransactionRepository
{
	override val allTransactions get() = transactionDataSource.allTransactions.map { it.map(TransactionModel::toEntity) }

	override suspend fun addTransaction(transaction: NewTransaction) = transactionDataSource.addTransaction(transaction.toModel())

	override suspend fun updateTransaction(transaction: Transaction) = transactionDataSource.updateTransaction(transaction.toModel())

	override suspend fun removeTransaction(transactionId: Long) = transactionDataSource.removeTransaction(transactionId)
}
