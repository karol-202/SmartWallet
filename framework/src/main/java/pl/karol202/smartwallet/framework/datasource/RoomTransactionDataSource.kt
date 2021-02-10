package pl.karol202.smartwallet.framework.datasource

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.TransactionDataSource
import pl.karol202.smartwallet.data.model.TransactionModel
import pl.karol202.smartwallet.framework.room.dao.TransactionDao
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity
import pl.karol202.smartwallet.framework.room.entity.toModel
import pl.karol202.smartwallet.framework.room.entity.toRoomEntity

class RoomTransactionDataSource(private val transactionsDao: TransactionDao) : TransactionDataSource
{
	override val allTransactions get() = transactionsDao.getAll().map { it.map(TransactionRoomEntity::toModel) }

	override fun getTransaction(transactionId: String) =
			transactionsDao.getById(transactionId).map { it?.toModel() }

	override suspend fun addTransaction(transaction: TransactionModel) =
			transactionsDao.insert(transaction.toRoomEntity())

	override suspend fun updateTransaction(transaction: TransactionModel) =
			transactionsDao.update(transaction.toRoomEntity())

	override suspend fun removeTransaction(transactionId: String) =
			transactionsDao.delete(transactionId)

	override suspend fun moveTransactionsBetweenSubcategories(sourceSubcategoryId: String, destinationSubcategoryId: String) =
			transactionsDao.moveBetweenSubcategories(sourceSubcategoryId, destinationSubcategoryId)

	override suspend fun removeTransactionsOfSubcategory(subcategoryId: String) =
			transactionsDao.deleteBySubcategory(subcategoryId)

	override suspend fun removeTransactionsOfAccount(accountId: String) =
			transactionsDao.deleteByAccount(accountId)
}
