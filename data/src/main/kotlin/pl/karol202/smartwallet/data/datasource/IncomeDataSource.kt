package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.ExpenseModel
import pl.karol202.smartwallet.data.model.IncomeModel
import pl.karol202.smartwallet.data.model.NewExpenseModel
import pl.karol202.smartwallet.data.model.NewIncomeModel

interface IncomeDataSource
{
    val allIncomes: Flow<List<IncomeModel>>

    suspend fun addIncome(income: NewIncomeModel)

    suspend fun updateIncome(income: IncomeModel)

    suspend fun removeIncome(incomeId: Long)
}
