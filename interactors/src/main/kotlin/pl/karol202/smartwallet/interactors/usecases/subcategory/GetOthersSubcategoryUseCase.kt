package pl.karol202.smartwallet.interactors.usecases.subcategory

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class GetOthersSubcategoryUseCase(override val function: suspend (Category.Type) -> Subcategory<Existing>) :
		SuspendUseCase1<Category.Type, Subcategory<Existing>>

fun getOthersSubcategoryUseCaseFactory(subcategoryRepository: SubcategoryRepository) = GetOthersSubcategoryUseCase { type ->
	subcategoryRepository.getOthersSubcategory(type).first()
}
