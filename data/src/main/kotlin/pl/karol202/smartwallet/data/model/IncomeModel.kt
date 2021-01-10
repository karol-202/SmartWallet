package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.Income

data class IncomeModel(val id: Long,
                       val amount: Double)

fun Income.toModel() = IncomeModel(id, amount)
fun IncomeModel.toEntity() = Income(id, amount)
