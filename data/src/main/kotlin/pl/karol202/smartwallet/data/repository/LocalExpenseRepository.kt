package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.ExpenseDataSource
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.domain.entity.Expense
import pl.karol202.smartwallet.domain.entity.NewExpense
import pl.karol202.smartwallet.domain.repository.ExpenseRepository

class LocalExpenseRepository(private val expenseDataSource: ExpenseDataSource) : ExpenseRepository
{
    override val allExpenses get() = expenseDataSource.allExpenses.map { it.toEntity() }

    override suspend fun addExpense(expense: NewExpense) = expenseDataSource.addExpense(expense.toModel())

    override suspend fun updateExpense(expense: Expense) = expenseDataSource.updateExpense(expense.toModel())

    override suspend fun removeExpense(expenseId: Long) = expenseDataSource.removeExpense(expenseId)
}
