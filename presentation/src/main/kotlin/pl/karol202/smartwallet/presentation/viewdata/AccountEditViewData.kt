package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.asId

data class AccountEditViewData(val name: String)
{
	fun withName(name: String) = copy(name = name)
}

fun Account<Existing>.toEditViewData() = AccountEditViewData(name)

fun AccountEditViewData.toEntity() = Account(New, name)

fun AccountEditViewData.toEntity(id: String) = Account(id.asId(), name)
