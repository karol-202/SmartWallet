package pl.karol202.smartwallet.presentation

import org.koin.dsl.module
import pl.karol202.smartwallet.presentation.viewmodel.accounts.AccountsViewModel
import pl.karol202.smartwallet.presentation.viewmodel.accounts.AccountsViewModelImpl
import pl.karol202.smartwallet.presentation.viewmodel.categories.CategoriesViewModel
import pl.karol202.smartwallet.presentation.viewmodel.categories.CategoriesViewModelImpl
import pl.karol202.smartwallet.presentation.viewmodel.categoryedit.CategoryEditViewModel
import pl.karol202.smartwallet.presentation.viewmodel.categoryedit.CategoryEditViewModelImpl
import pl.karol202.smartwallet.presentation.viewmodel.subcategoryedit.SubcategoryEditViewModel
import pl.karol202.smartwallet.presentation.viewmodel.subcategoryedit.SubcategoryEditViewModelImpl
import pl.karol202.smartwallet.presentation.viewmodel.transactionedit.TransactionEditViewModel
import pl.karol202.smartwallet.presentation.viewmodel.transactionedit.TransactionEditViewModelImpl
import pl.karol202.smartwallet.presentation.viewmodel.transactions.TransactionsViewModel
import pl.karol202.smartwallet.presentation.viewmodel.transactions.TransactionsViewModelImpl

fun presentationModule() = module {
	factory<TransactionsViewModel> { TransactionsViewModelImpl(get()) }
	factory<TransactionEditViewModel> { TransactionEditViewModelImpl(get(), get(), get(), get(), get(), get()) }
	factory<CategoriesViewModel> { CategoriesViewModelImpl(get()) }
	factory<CategoryEditViewModel> { CategoryEditViewModelImpl(get(), get(), get(), get(), get()) }
	factory<SubcategoryEditViewModel> { SubcategoryEditViewModelImpl(get(), get(), get(), get(), get(), get()) }
	factory<AccountsViewModel> { AccountsViewModelImpl(get()) }
}
