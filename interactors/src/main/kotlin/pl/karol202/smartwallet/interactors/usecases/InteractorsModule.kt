package pl.karol202.smartwallet.interactors.usecases

import org.koin.dsl.module
import pl.karol202.smartwallet.interactors.usecases.account.*
import pl.karol202.smartwallet.interactors.usecases.category.*
import pl.karol202.smartwallet.interactors.usecases.init.initializeRepositoriesUseCaseFactory
import pl.karol202.smartwallet.interactors.usecases.subcategory.*
import pl.karol202.smartwallet.interactors.usecases.transaction.*

fun interactorsModule() = module {
	single { initializeRepositoriesUseCaseFactory(get(), get()) }

	single { getTransactionsWithCategoriesFlowUseCaseFactory(get(), get(), get()) }
	single { getTransactionUseCaseFactory(get()) }
	single { addTransactionUseCaseFactory(get()) }
	single { updateTransactionUseCaseFactory(get()) }
	single { removeTransactionUseCaseFactory(get()) }

	single { getCategoriesFlowUseCaseFactory(get()) }
	single { getCategoriesWithSubcategoriesFlowUseCaseFactory(get(), get()) }
	single { getCategoryUseCaseFactory(get()) }
	single { addCategoryUseCaseFactory(get()) }
	single { updateCategoryUseCaseFactory(get()) }
	single { removeCategoryUseCaseFactory(get(), get()) }

	single { getSubcategoryUseCaseFactory(get()) }
	single { getOthersSubcategoryUseCaseFactory(get()) }
	single { getSubcategoriesFlowUseCaseFactory(get()) }
	single { addSubcategoryUseCaseFactory(get()) }
	single { updateSubcategoryUseCaseFactory(get()) }
	single { removeSubcategoryUseCaseFactory(get(), get(), get()) }

	single { getAccountsFlowUseCaseFactory(get()) }
	single { getAccountsWithDefaultFlowUseCaseFactory(get()) }
	single { getAccountUseCaseFactory(get()) }
	single { getDefaultAccountUseCaseFactory(get()) }
	single { markAccountAsDefaultUseCaseFactory(get()) }
	single { addAccountUseCaseFactory(get()) }
	single { updateAccountUseCaseFactory(get()) }
	single { removeAccountUseCaseFactory(get(), get()) }
}
