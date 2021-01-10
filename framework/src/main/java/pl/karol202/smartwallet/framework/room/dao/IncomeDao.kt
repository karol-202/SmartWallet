package pl.karol202.smartwallet.framework.room.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.framework.room.entity.ExpenseRoomEntity
import pl.karol202.smartwallet.framework.room.entity.IncomeRoomEntity

@Dao
interface IncomeDao
{
	@Insert
	fun insert(income: IncomeRoomEntity)

	@Update
	fun update(income: IncomeRoomEntity)

	@Query("DELETE FROM incomes WHERE id = :incomeId")
	fun delete(incomeId: Long)

	@Query("SELECT * FROM incomes")
	fun getAll(): Flow<List<IncomeRoomEntity>>
}
