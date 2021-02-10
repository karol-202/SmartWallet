package pl.karol202.smartwallet.framework.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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

	@Query("UPDATE transactions SET subcategoryId = :destinationSubcategoryId WHERE subcategoryId = :sourceSubcategoryId")
	suspend fun moveBetweenSubcategories(sourceSubcategoryId: String, destinationSubcategoryId: String)

	@Query("DELETE FROM transactions WHERE subcategoryId = :subcategoryId")
	suspend fun deleteBySubcategory(subcategoryId: String)

	@Query("DELETE FROM transactions WHERE accountId = :accountId")
	suspend fun deleteByAccount(accountId: String)

	@Query("SELECT * FROM transactions")
	fun getAll(): Flow<List<TransactionRoomEntity>>

	@Query("SELECT * FROM transactions WHERE id = :id")
	fun getById(id: String): Flow<TransactionRoomEntity?>
}
