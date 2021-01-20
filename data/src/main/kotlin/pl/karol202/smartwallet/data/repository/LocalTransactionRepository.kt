package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.IdDataSource
import pl.karol202.smartwallet.data.datasource.TransactionDataSource
import pl.karol202.smartwallet.data.model.TransactionModel
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.TransactionRepository

class LocalTransactionRepository(private val transactionDataSource: TransactionDataSource,
                                 private val idDataSource: IdDataSource) : TransactionRepository
{
	override val allTransactions
		get() = transactionDataSource.allTransactions.map { it.map(TransactionModel::toEntity) }

	override fun getTransaction(transactionId: String) =
			transactionDataSource.getTransaction(transactionId).map { it?.toEntity() }

	override suspend fun addTransaction(transaction: Transaction<New>) =
			transactionDataSource.addTransaction(transaction.toModel(idDataSource.createRandomId()))

	override suspend fun updateTransaction(transaction: Transaction<Existing>) =
			transactionDataSource.updateTransaction(transaction.toModel())

	override suspend fun removeTransaction(transactionId: String) =
			transactionDataSource.removeTransaction(transactionId)
}
