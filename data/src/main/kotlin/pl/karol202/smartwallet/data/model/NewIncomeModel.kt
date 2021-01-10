package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.NewIncome

data class NewIncomeModel(val amount: Double)

fun NewIncome.toModel() = NewIncomeModel(amount)
