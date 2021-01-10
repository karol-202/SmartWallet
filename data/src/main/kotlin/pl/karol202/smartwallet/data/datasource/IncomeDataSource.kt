package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.ExpenseModel
import pl.karol202.smartwallet.data.model.IncomeModel
import pl.karol202.smartwallet.data.model.NewExpenseModel

interface IncomeDataSource
{
    val allIncomes: Flow<IncomeModel>

    suspend fun addIncome(income: IncomeModel): ExpenseModel

    suspend fun updateIncome(income: IncomeModel)

    suspend fun removeIncome(incomeId: Long)
}
