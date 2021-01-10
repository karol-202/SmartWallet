package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.ExpenseModel
import pl.karol202.smartwallet.data.model.NewExpenseModel

interface ExpenseDataSource
{
    val allExpenses: Flow<List<ExpenseModel>>

    suspend fun addExpense(expense: NewExpenseModel)

    suspend fun updateExpense(expense: ExpenseModel)

    suspend fun removeExpense(expenseId: Long)
}
