package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.SubcategoryModel

interface SubcategoryDataSource
{
    val allSubcategories: Flow<List<SubcategoryModel>>

    fun getSubcategory(subcategoryId: String): Flow<SubcategoryModel?>

    suspend fun addSubcategory(subcategory: SubcategoryModel)

    suspend fun updateSubcategory(subcategory: SubcategoryModel)

    suspend fun removeSubcategory(subcategoryId: String)

    suspend fun moveSubcategories(sourceCategoryId: String, destinationCategoryId: String)
}
