package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.ExpenseDataSource
import pl.karol202.smartwallet.data.datasource.IncomeDataSource
import pl.karol202.smartwallet.data.model.ExpenseModel
import pl.karol202.smartwallet.data.model.IncomeModel
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.domain.entity.Expense
import pl.karol202.smartwallet.domain.entity.Income
import pl.karol202.smartwallet.domain.entity.NewExpense
import pl.karol202.smartwallet.domain.entity.NewIncome
import pl.karol202.smartwallet.domain.repository.ExpenseRepository
import pl.karol202.smartwallet.domain.repository.IncomeRepository

class LocalIncomeRepository(private val expenseDataSource: IncomeDataSource) : IncomeRepository
{
	override val allIncomes get() = expenseDataSource.allIncomes.map { it.map(IncomeModel::toEntity) }

	override suspend fun addIncome(income: NewIncome) = expenseDataSource.addIncome(income.toModel())

	override suspend fun updateIncome(income: Income) = expenseDataSource.updateIncome(income.toModel())

	override suspend fun removeIncome(incomeId: Long) = expenseDataSource.removeIncome(incomeId)
}
