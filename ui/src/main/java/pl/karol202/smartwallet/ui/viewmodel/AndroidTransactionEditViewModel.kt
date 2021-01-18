package pl.karol202.smartwallet.ui.viewmodel

import pl.karol202.smartwallet.presentation.viewmodel.transactionedit.TransactionEditViewModel

class AndroidTransactionEditViewModel(private val delegate: TransactionEditViewModel) : AndroidViewModelProxy(delegate),
                                                                                        TransactionEditViewModel by delegate
