package pl.karol202.smartwallet.framework.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.framework.room.entity.TransactionRoomEntity

@Dao
interface TransactionDao
{
	@Insert
	suspend fun insert(transaction: TransactionRoomEntity)

	@Update
	suspend fun update(transaction: TransactionRoomEntity)

	@Query("DELETE FROM transactions WHERE id = :id")
	suspend fun delete(id: String)

	@Query("SELECT * FROM transactions")
	fun getAll(): Flow<List<TransactionRoomEntity>>

	@Query("SELECT * FROM transactions WHERE id = :id")
	fun getById(id: String): Flow<TransactionRoomEntity?>
}
