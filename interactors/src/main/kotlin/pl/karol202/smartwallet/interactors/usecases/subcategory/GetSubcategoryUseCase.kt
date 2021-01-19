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

class GetSubcategoryUseCase(override val function: suspend (String) -> Subcategory<Existing>) :
		SuspendUseCase1<String, Subcategory<Existing>>

fun getSubcategoryUseCaseFactory(subcategoryRepository: SubcategoryRepository) = GetSubcategoryUseCase { id ->
	subcategoryRepository.getSubcategory(id).first()
}
