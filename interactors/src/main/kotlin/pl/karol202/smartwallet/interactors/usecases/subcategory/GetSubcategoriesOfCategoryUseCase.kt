package pl.karol202.smartwallet.interactors.usecases.subcategory

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.domain.repository.TransactionRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class GetSubcategoriesOfCategoryUseCase(override val function: suspend (String) -> List<Subcategory<Existing>>) :
		SuspendUseCase1<String, List<Subcategory<Existing>>>

fun getSubcategoriesOfCategoryUseCaseFactory(subcategoryRepository: SubcategoryRepository) =
		GetSubcategoriesOfCategoryUseCase { id ->
			subcategoryRepository.getSubcategoriesOfCategory(id).first()
		}
