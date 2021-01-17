package pl.karol202.smartwallet.presentation

import org.koin.dsl.module
import pl.karol202.smartwallet.presentation.viewmodel.TransactionEditViewModel
import pl.karol202.smartwallet.presentation.viewmodel.TransactionsViewModel
import pl.karol202.smartwallet.presentation.viewmodel.impl.TransactionEditViewModelImpl
import pl.karol202.smartwallet.presentation.viewmodel.impl.TransactionsViewModelImpl

fun presentationModule() = module {
	factory<TransactionsViewModel> { TransactionsViewModelImpl(get()) }
	factory<TransactionEditViewModel> { TransactionEditViewModelImpl(get(), get(), get()) }
}
