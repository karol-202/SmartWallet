package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Account
import pl.karol202.smartwallet.domain.entity.Existing

data class AccountItemViewData(val id: String,
                               val name: String)

fun Account<Existing>.toItemViewData() = AccountItemViewData(id.value, name)
