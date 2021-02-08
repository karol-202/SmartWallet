package pl.karol202.smartwallet.ui.viewmodel

import pl.karol202.smartwallet.presentation.viewmodel.accounts.AccountsViewModel

class AndroidAccountsViewModel(private val delegate: AccountsViewModel) : AndroidViewModelProxy(delegate),
                                                                          AccountsViewModel by delegate
