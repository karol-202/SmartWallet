package pl.karol202.smartwallet.interactors.usecases

import org.koin.dsl.module
import pl.karol202.smartwallet.interactors.usecases.category.*
import pl.karol202.smartwallet.interactors.usecases.subcategory.*
import pl.karol202.smartwallet.interactors.usecases.transaction.*

fun interactorsModule() = module {
	single { getTransactionsFlowUseCaseFactory(get()) }
	single { getTransactionUseCaseFactory(get()) }
	single { addTransactionUseCaseFactory(get()) }
	single { updateTransactionUseCaseFactory(get()) }
	single { removeTransactionUseCaseFactory(get()) }

	single { getCategoriesFlowUseCaseFactory(get()) }
	single { getCategoryUseCaseFactory(get()) }
	single { addCategoryUseCaseFactory(get()) }
	single { updateCategoryUseCaseFactory(get()) }
	single { removeCategoryUseCaseFactory(get()) }

	single { getSubcategoryUseCaseFactory(get()) }
	single { getSubcategoriesOfCategoryUseCaseFactory(get()) }
	single { addSubcategoryUseCaseFactory(get()) }
	single { updateSubcategoryUseCaseFactory(get()) }
	single { removeSubcategoryUseCaseFactory(get()) }
}
