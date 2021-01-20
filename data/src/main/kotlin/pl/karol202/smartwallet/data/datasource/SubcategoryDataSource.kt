package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.SubcategoryModel

interface SubcategoryDataSource
{
    fun getSubcategory(subcategoryId: String): Flow<SubcategoryModel?>

    fun getSubcategoriesOfCategory(categoryId: String): Flow<List<SubcategoryModel>>

    suspend fun addSubcategory(subcategory: SubcategoryModel)

    suspend fun updateSubcategory(subcategory: SubcategoryModel)

    suspend fun removeSubcategory(subcategoryId: String)
}
