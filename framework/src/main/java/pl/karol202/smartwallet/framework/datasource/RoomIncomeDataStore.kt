package pl.karol202.smartwallet.framework.datasource

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.ExpenseDataSource
import pl.karol202.smartwallet.data.datasource.IncomeDataSource
import pl.karol202.smartwallet.data.model.ExpenseModel
import pl.karol202.smartwallet.data.model.IncomeModel
import pl.karol202.smartwallet.data.model.NewExpenseModel
import pl.karol202.smartwallet.data.model.NewIncomeModel
import pl.karol202.smartwallet.framework.room.dao.ExpenseDao
import pl.karol202.smartwallet.framework.room.dao.IncomeDao
import pl.karol202.smartwallet.framework.room.entity.ExpenseRoomEntity
import pl.karol202.smartwallet.framework.room.entity.IncomeRoomEntity
import pl.karol202.smartwallet.framework.room.entity.toEntity
import pl.karol202.smartwallet.framework.room.entity.toRoomEntity

class RoomIncomeDataStore(private val incomeDao: IncomeDao) : IncomeDataSource
{
	override val allIncomes get() = incomeDao.getAll().map { it.map(IncomeRoomEntity::toEntity) }

	override suspend fun addIncome(income: NewIncomeModel) = incomeDao.insert(income.toRoomEntity())

	override suspend fun updateIncome(income: IncomeModel) = incomeDao.update(income.toRoomEntity())

	override suspend fun removeIncome(incomeId: Long) = incomeDao.delete(incomeId)
}
