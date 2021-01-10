package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.Expense

data class ExpenseModel(val id: Long,
                        val amount: Double)

fun Expense.toModel() = ExpenseModel(id, amount)
fun ExpenseModel.toEntity() = Expense(id, amount)
