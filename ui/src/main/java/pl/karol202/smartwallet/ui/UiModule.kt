package pl.karol202.smartwallet.ui

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.karol202.smartwallet.ui.viewmodel.AndroidTransactionEditViewModel
import pl.karol202.smartwallet.ui.viewmodel.AndroidTransactionsViewModel

fun uiModule() = module {
	viewModel { AndroidTransactionsViewModel(get()) }
	viewModel { AndroidTransactionEditViewModel(get()) }
}
