package pl.karol202.smartwallet.interactors.usecases.subcategory

import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class RemoveSubcategoryUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun removeSubcategoryUseCaseFactory(subcategoryRepository: SubcategoryRepository) = RemoveSubcategoryUseCase { id ->
	subcategoryRepository.removeSubcategory(id)
}
