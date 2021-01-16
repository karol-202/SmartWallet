package pl.karol202.smartwallet.framework.datasource

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.TransactionDataSource
import pl.karol202.smartwallet.data.model.NewTransactionModel
import pl.karol202.smartwallet.data.model.TransactionModel
import pl.karol202.smartwallet.framework.room.dao.TransactionDao
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity
import pl.karol202.smartwallet.framework.room.entity.toModel
import pl.karol202.smartwallet.framework.room.entity.toRoomEntity

class RoomTransactionDataStore(private val transactionsDao: TransactionDao) : TransactionDataSource
{
	override val allTransactions get() = transactionsDao.getAll().map { it.map(TransactionRoomEntity::toModel) }

	override fun getTransaction(transactionId: Long) =
			transactionsDao.getById(transactionId).map(TransactionRoomEntity::toModel)

	override suspend fun addTransaction(transaction: NewTransactionModel) =
			transactionsDao.insert(transaction.toRoomEntity())

	override suspend fun updateTransaction(transaction: TransactionModel) =
			transactionsDao.update(transaction.toRoomEntity())

	override suspend fun removeTransaction(transactionId: Long) =
			transactionsDao.delete(transactionId)
}
