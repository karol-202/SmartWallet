package pl.karol202.smartwallet.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Expense
import pl.karol202.smartwallet.domain.entity.NewExpense

interface ExpenseRepository
{
    val allExpenses: Flow<List<Expense>>

    suspend fun addExpense(expense: NewExpense)

    suspend fun updateExpense(expense: Expense)

    suspend fun removeExpense(expenseId: Long)
}
