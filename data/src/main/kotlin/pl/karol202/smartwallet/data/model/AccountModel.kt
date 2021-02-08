package pl.karol202.smartwallet.data.model

import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.asId

data class AccountModel(val id: String,
                        val name: String)

fun Account<New>.toModel(newId: String) = AccountModel(newId, name)

fun Account<Existing>.toModel() = AccountModel(id.value, name)

fun AccountModel.toEntity() = Account(id.asId(), name)
