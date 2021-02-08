package pl.karol202.smartwallet.interactors.usecases.category

import kotlinx.coroutines.flow.first
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.SuspendUseCase2
import pl.karol202.smartwallet.interactors.usecases.category.RemoveCategoryUseCase.SubcategoriesPolicy

class RemoveCategoryUseCase(override val function: suspend (String, SubcategoriesPolicy) -> Unit) :
		SuspendUseCase2<String, SubcategoriesPolicy, Unit>
{
	enum class SubcategoriesPolicy
	{
		REMOVE, MOVE_TO_OTHERS
	}
}

fun removeCategoryUseCaseFactory(categoryRepository: CategoryRepository,
                                 subcategoryRepository: SubcategoryRepository) = RemoveCategoryUseCase { id, policy ->
	when(policy)
	{
		SubcategoriesPolicy.MOVE_TO_OTHERS -> moveSubcategoriesToOthers(categoryRepository, subcategoryRepository, id)
		SubcategoriesPolicy.REMOVE -> Unit /* Expected to be fulfilled by database (CASCADE) */
	}

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
