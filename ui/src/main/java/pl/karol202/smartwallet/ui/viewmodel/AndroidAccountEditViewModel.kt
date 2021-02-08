package pl.karol202.smartwallet.ui.viewmodel

import pl.karol202.smartwallet.presentation.viewmodel.accountsedit.AccountEditViewModel

class AndroidAccountEditViewModel(private val delegate: AccountEditViewModel) : AndroidViewModelProxy(delegate),
                                                                                AccountEditViewModel by delegate
