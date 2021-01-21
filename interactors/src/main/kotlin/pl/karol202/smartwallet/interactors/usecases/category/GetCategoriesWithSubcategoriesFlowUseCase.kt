package pl.karol202.smartwallet.interactors.usecases.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineLatest
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.usecases.UseCase0

class GetCategoriesWithSubcategoriesFlowUseCase(
		override val function: () -> Flow<Map<Category<Existing>, List<Subcategory<Existing>>>>
) : UseCase0<Flow<Map<Category<Existing>, List<Subcategory<Existing>>>>>

fun getCategoriesWithSubcategoriesFlowUseCaseFactory(categoryRepository: CategoryRepository,
                                                     subcategoryRepository: SubcategoryRepository) =
		GetCategoriesWithSubcategoriesFlowUseCase {
			categoryRepository.allCategories.combine(subcategoryRepository.allSubcategories) { categories, subcategories ->
				categories.associateWith { category ->
					subcategories.filter { it.categoryId == category.id.value }
				}
			}
		}
