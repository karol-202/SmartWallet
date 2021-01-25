package pl.karol202.smartwallet.interactors.usecases.category

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.combineLatest
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.entity.Transaction
import pl.karol202.smartwallet.domain.repository.CategoryRepository
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository
import pl.karol202.smartwallet.interactors.filter.FilterFunction
import pl.karol202.smartwallet.interactors.filter.FilterScope
import pl.karol202.smartwallet.interactors.filter.filterWith
import pl.karol202.smartwallet.interactors.usecases.UseCase0
import pl.karol202.smartwallet.interactors.usecases.UseCase1

data class CategoryWithSubcategories(val category: Category<Existing>,
                                     val subcategories: List<Subcategory<Existing>>)

class GetCategoriesWithSubcategoriesFlowUseCase(
		override val function: (FilterFunction<CategoryWithSubcategories>) -> Flow<List<CategoryWithSubcategories>>
) : UseCase1<FilterFunction<CategoryWithSubcategories>, Flow<List<CategoryWithSubcategories>>>

fun getCategoriesWithSubcategoriesFlowUseCaseFactory(categoryRepository: CategoryRepository,
                                                     subcategoryRepository: SubcategoryRepository) =
		GetCategoriesWithSubcategoriesFlowUseCase { filter ->
			categoryRepository.allCategories.combine(subcategoryRepository.allSubcategories) { categories, subcategories ->
				categories.map { category ->
					CategoryWithSubcategories(category, subcategories.filter { it.categoryId == category.id.value })
				}.filterWith(filter)
			}
		}

fun FilterScope<CategoryWithSubcategories>.filterByTransactionType(transaction: Transaction<*>) =
		filter { it.category.type == transaction.getCorrespondingCategoryType() }

private fun Transaction<*>.getCorrespondingCategoryType() = when(this)
{
	is Transaction.Expense -> Category.Type.EXPENSE
	is Transaction.Income -> Category.Type.INCOME
}
