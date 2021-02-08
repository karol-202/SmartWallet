package pl.karol202.smartwallet.ui

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.karol202.smartwallet.ui.viewmodel.*

fun uiModule() = module {
	viewModel { AndroidTransactionsViewModel(get()) }
	viewModel { AndroidTransactionEditViewModel(get()) }
	viewModel { AndroidCategoriesViewModel(get()) }
	viewModel { AndroidCategoryEditViewModel(get()) }
	viewModel { AndroidSubcategoryEditViewModel(get()) }
	viewModel { AndroidAccountsViewModel(get()) }
}
