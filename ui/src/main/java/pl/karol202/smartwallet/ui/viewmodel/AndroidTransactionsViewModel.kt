package pl.karol202.smartwallet.ui.viewmodel

import pl.karol202.smartwallet.presentation.viewmodel.TransactionsViewModel

class AndroidTransactionsViewModel(private val delegate: TransactionsViewModel) : AndroidViewModelProxy(delegate),
                                                                                  TransactionsViewModel by delegate
