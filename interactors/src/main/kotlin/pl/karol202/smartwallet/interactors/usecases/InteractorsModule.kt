package pl.karol202.smartwallet.interactors.usecases

import org.koin.dsl.module
import pl.karol202.smartwallet.interactors.usecases.transactions.addTransactionUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.transactions.getTransactionUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.transactions.getTransactionsFlowUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.transactions.updateTransactionUseCaseFactory

fun interactorsModule() = module {
	single { getTransactionsFlowUseCaseFactory(get()) }
	single { getTransactionUseCaseFactory(get()) }
	single { addTransactionUseCaseFactory(get()) }
	single { updateTransactionUseCaseFactory(get()) }
}
