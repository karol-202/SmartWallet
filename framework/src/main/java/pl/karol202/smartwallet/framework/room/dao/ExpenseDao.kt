package pl.karol202.smartwallet.framework.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.framework.room.entity.ExpenseRoomEntity

@Dao
interface ExpenseDao
{
	@Insert
	fun insert(expense: ExpenseRoomEntity)

	@Update
	fun update(expense: ExpenseRoomEntity)

	@Query("DELETE FROM expenses WHERE id = :expenseId")
	fun delete(expenseId: Long)

	@Query("SELECT * FROM expenses")
	fun getAll(): Flow<List<ExpenseRoomEntity>>
}
