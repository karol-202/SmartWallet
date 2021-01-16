package pl.karol202.smartwallet.interactors.usecases

import org.koin.dsl.module
import pl.karol202.smartwallet.interactors.usecases.transactions.getTransactionUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.transactions.getTransactionsFlowUseCaseFactory

fun interactorsModule() = module {
	single { getTransactionsFlowUseCaseFactory(get()) }
	single { getTransactionUseCaseFactory(get()) }
}
