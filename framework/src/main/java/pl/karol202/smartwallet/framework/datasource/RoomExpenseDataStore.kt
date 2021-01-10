package pl.karol202.smartwallet.framework.datasource

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.ExpenseDataSource
import pl.karol202.smartwallet.data.model.ExpenseModel
import pl.karol202.smartwallet.data.model.NewExpenseModel
import pl.karol202.smartwallet.framework.room.dao.ExpenseDao
import pl.karol202.smartwallet.framework.room.entity.ExpenseRoomEntity
import pl.karol202.smartwallet.framework.room.entity.toEntity
import pl.karol202.smartwallet.framework.room.entity.toRoomEntity

class RoomExpenseDataStore(private val expenseDao: ExpenseDao) : ExpenseDataSource
{
	override val allExpenses get() = expenseDao.getAll().map { it.map(ExpenseRoomEntity::toEntity) }

	override suspend fun addExpense(expense: NewExpenseModel) = expenseDao.insert(expense.toRoomEntity())

	override suspend fun updateExpense(expense: ExpenseModel) = expenseDao.update(expense.toRoomEntity())

	override suspend fun removeExpense(expenseId: Long) = expenseDao.delete(expenseId)
}
