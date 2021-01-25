package pl.karol202.smartwallet.presentation.viewdata

import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.interactors.usecases.category.CategoryWithSubcategories

data class CategoryWithSubcategoriesItemViewData(val category: CategoryItemViewData,
                                                 val subcategories: List<SubcategoryItemViewData>)

fun CategoryWithSubcategories.toItemViewData() = category.toItemViewData(subcategories)

fun Category<Existing>.toItemViewData(subcategories: List<Subcategory<Existing>>) =
		CategoryWithSubcategoriesItemViewData(toItemViewData(), subcategories.map(Subcategory<Existing>::toItemViewData))
