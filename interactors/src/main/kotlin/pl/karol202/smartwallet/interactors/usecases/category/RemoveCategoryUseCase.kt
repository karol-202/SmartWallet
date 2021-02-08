package pl.karol202.smartwallet.interactors.usecases.category

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase1

class RemoveCategoryUseCase(override val function: suspend (String) -> Unit) : SuspendUseCase1<String, Unit>

fun removeCategoryUseCaseFactory(categoryRepository: CategoryRepository,
                                 subcategoryRepository: SubcategoryRepository) = RemoveCategoryUseCase { id ->
	moveSubcategoriesToOthers(categoryRepository, subcategoryRepository, id)
	categoryRepository.removeCategory(id)
}

private suspend fun moveSubcategoriesToOthers(categoryRepository: CategoryRepository,
                                              subcategoryRepository: SubcategoryRepository,
                                              sourceId: String)
{
	val source = categoryRepository.getCategory(sourceId).first() ?: error("Category does not exist")
	val destinationId = categoryRepository.getOthersCategoryId(source.type)
	subcategoryRepository.moveSubcategories(sourceId, destinationId)
}
