package pl.karol202.smartwallet.interactors.usecases

import org.koin.dsl.module
import pl.karol202.smartwallet.interactors.usecases.category.addCategoryUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.category.getCategoriesFlowUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.category.getCategoryUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.category.updateCategoryUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.transaction.addTransactionUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.transaction.getTransactionUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.transaction.getTransactionsFlowUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.transaction.updateTransactionUseCaseFactory

fun interactorsModule() = module {
	single { getTransactionsFlowUseCaseFactory(get()) }
	single { getTransactionUseCaseFactory(get()) }
	single { addTransactionUseCaseFactory(get()) }
	single { updateTransactionUseCaseFactory(get()) }

	single { getCategoriesFlowUseCaseFactory(get()) }
	single { getCategoryUseCaseFactory(get()) }
	single { addCategoryUseCaseFactory(get()) }
	single { updateCategoryUseCaseFactory(get()) }
}
