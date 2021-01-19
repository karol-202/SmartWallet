package pl.karol202.smartwallet.interactors.usecases.subcategory

import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class AddSubcategoryUseCase(override val function: suspend (Subcategory<New>) -> Unit) :
		SuspendUseCase1<Subcategory<New>, Unit>

fun addSubcategoryUseCaseFactory(subcategoryRepository: SubcategoryRepository) = AddSubcategoryUseCase { subcategory ->
	subcategoryRepository.addSubcategory(subcategory)
}
