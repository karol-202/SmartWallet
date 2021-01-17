package pl.karol202.smartwallet.domain.repository

import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Subcategory

interface SubcategoryRepository
{
	suspend fun addSubcategory(subcategory: Subcategory<New>)

	suspend fun updateSubcategory(subcategory: Subcategory<Existing>)

	suspend fun removeSubcategory(subcategoryId: String)
}
