package pl.karol202.smartwallet.data

import org.koin.dsl.module
import pl.karol202.smartwallet.data.repository.LocalAccountRepository
import pl.karol202.smartwallet.data.repository.LocalCategoryRepository
import pl.karol202.smartwallet.data.repository.LocalSubcategoryRepository
import pl.karol202.smartwallet.data.repository.LocalTransactionRepository
import pl.karol202.smartwallet.domain.repository.AccountRepository
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository

fun dataModule() = module {
	single<TransactionRepository> { LocalTransactionRepository(get(), get()) }
	single<CategoryRepository> { LocalCategoryRepository(get(), get(), get()) }
	single<SubcategoryRepository> { LocalSubcategoryRepository(get(), get(), get(), get()) }
	single<AccountRepository> { LocalAccountRepository(get(), get(), get()) }
}
