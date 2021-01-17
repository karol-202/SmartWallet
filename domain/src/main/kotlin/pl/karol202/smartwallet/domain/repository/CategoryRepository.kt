package pl.karol202.smartwallet.domain.repository

import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New

interface CategoryRepository
{
	suspend fun addCategory(category: Category<New>)

	suspend fun updateCategory(category: Category<Existing>)

	suspend fun removeCategory(categoryId: String)
}
