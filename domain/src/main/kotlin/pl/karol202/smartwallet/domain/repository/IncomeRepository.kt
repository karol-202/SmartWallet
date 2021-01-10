package pl.karol202.smartwallet.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Expense
import pl.karol202.smartwallet.domain.entity.Income
import pl.karol202.smartwallet.domain.entity.NewExpense
import pl.karol202.smartwallet.domain.entity.NewIncome

interface IncomeRepository
{
    val allIncomes: Flow<List<Income>>

    suspend fun addIncome(income: NewIncome)

    suspend fun updateIncome(income: Income)

    suspend fun removeIncome(incomeId: Long)
}
