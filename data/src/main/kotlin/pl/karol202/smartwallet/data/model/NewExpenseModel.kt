package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.NewExpense

data class NewExpenseModel(val amount: Double)

fun NewExpense.toModel() = NewExpenseModel(amount)
