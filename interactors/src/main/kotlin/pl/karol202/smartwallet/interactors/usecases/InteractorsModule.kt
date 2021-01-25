package pl.karol202.smartwallet.interactors.usecases

import org.koin.dsl.module
import pl.karol202.smartwallet.interactors.usecases.category.*
import pl.karol202.smartwallet.interactors.usecases.subcategory.*
import pl.karol202.smartwallet.interactors.usecases.transaction.*

fun interactorsModule() = module {
	single { getTransactionsWithCategoriesFlowUseCaseFactory(get(), get()) }
	single { getTransactionUseCaseFactory(get()) }
	single { addTransactionUseCaseFactory(get()) }
	single { updateTransactionUseCaseFactory(get()) }
	single { removeTransactionUseCaseFactory(get()) }

	single { getCategoriesFlowUseCaseFactory(get()) }
	single { getCategoriesWithSubcategoriesFlowUseCaseFactory(get(), get()) }
	single { getCategoryUseCaseFactory(get()) }
	single { addCategoryUseCaseFactory(get()) }
	single { updateCategoryUseCaseFactory(get()) }
	single { removeCategoryUseCaseFactory(get()) }

	single { getSubcategoryUseCaseFactory(get()) }
	single { getSubcategoriesFlowUseCaseFactory(get()) }
	single { addSubcategoryUseCaseFactory(get()) }
	single { updateSubcategoryUseCaseFactory(get()) }
	single { removeSubcategoryUseCaseFactory(get()) }
}
