package pl.karol202.smartwallet.presentation

import org.koin.dsl.module
import pl.karol202.smartwallet.presentation.viewmodel.TransactionsViewModel
import pl.karol202.smartwallet.presentation.viewmodel.impl.TransactionsViewModelImpl

fun presentationModule() = module {
	single<TransactionsViewModel> { TransactionsViewModelImpl(get()) }
}
