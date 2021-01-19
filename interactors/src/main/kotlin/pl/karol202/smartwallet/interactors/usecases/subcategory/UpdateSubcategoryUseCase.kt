package pl.karol202.smartwallet.interactors.usecases.subcategory

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class UpdateSubcategoryUseCase(override val function: suspend (Subcategory<Existing>) -> Unit) :
		SuspendUseCase1<Subcategory<Existing>, Unit>

fun updateSubcategoryUseCaseFactory(subcategoryRepository: SubcategoryRepository) = UpdateSubcategoryUseCase { subcategory ->
	subcategoryRepository.updateSubcategory(subcategory)
}
