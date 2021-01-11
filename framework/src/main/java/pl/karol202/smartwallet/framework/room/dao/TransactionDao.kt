package pl.karol202.smartwallet.framework.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity

@Dao
interface TransactionDao
{
	@Insert
	fun insert(transaction: TransactionRoomEntity)

	@Update
	fun update(transaction: TransactionRoomEntity)

	@Query("DELETE FROM transactions WHERE id = :id")
	fun delete(id: Long)

	@Query("SELECT * FROM transactions")
	fun getAll(): Flow<List<TransactionRoomEntity>>
}
