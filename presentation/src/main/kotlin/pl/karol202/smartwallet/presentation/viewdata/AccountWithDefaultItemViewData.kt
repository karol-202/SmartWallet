package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.interactors.usecases.account.AccountWithDefault

data class AccountWithDefaultItemViewData(val id: String,
                                          val name: String,
                                          val isDefault: Boolean)

fun AccountWithDefault.toItemViewData() = AccountWithDefaultItemViewData(account.id.value, account.name, isDefault)
