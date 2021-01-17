package pl.karol202.smartwallet.data

import org.koin.dsl.module
import pl.karol202.smartwallet.data.repository.LocalTransactionRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository

fun dataModule() = module {
	single<TransactionRepository> { LocalTransactionRepository(get(), get()) }
}
