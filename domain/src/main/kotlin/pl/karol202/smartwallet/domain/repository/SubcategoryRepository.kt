package pl.karol202.smartwallet.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Subcategory

interface SubcategoryRepository
{
	fun getSubcategory(subcategoryId: String): Flow<Subcategory<Existing>>

	fun getSubcategoriesOfCategory(categoryId: String): Flow<List<Subcategory<Existing>>>

	suspend fun addSubcategory(subcategory: Subcategory<New>)

	suspend fun updateSubcategory(subcategory: Subcategory<Existing>)

	suspend fun removeSubcategory(subcategoryId: String)
}
