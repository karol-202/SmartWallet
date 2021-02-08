package pl.karol202.smartwallet.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Subcategory

interface SubcategoryRepository
{
	val allSubcategories: Flow<List<Subcategory<Existing>>>

	fun getOthersSubcategory(type: Category.Type): Flow<Subcategory<Existing>>

	fun getSubcategory(subcategoryId: String): Flow<Subcategory<Existing>?>

	suspend fun addSubcategory(subcategory: Subcategory<New>)

	suspend fun updateSubcategory(subcategory: Subcategory<Existing>)

	suspend fun removeSubcategory(subcategoryId: String)

	suspend fun moveSubcategories(sourceCategoryId: String, destinationCategoryId: String)

	suspend fun ensureIntegrity()
}
