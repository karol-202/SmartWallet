package pl.karol202.smartwallet.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New

interface CategoryRepository
{
	val allCategories: Flow<List<Category<Existing>>>

	fun getCategory(categoryId: String): Flow<Category<Existing>>

	suspend fun addCategory(category: Category<New>)

	suspend fun updateCategory(category: Category<Existing>)

	suspend fun removeCategory(categoryId: String)
}
