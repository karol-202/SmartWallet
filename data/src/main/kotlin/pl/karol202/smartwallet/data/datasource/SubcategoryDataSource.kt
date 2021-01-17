package pl.karol202.smartwallet.data.datasource

import pl.karol202.smartwallet.data.model.SubcategoryModel

interface SubcategoryDataSource
{
    suspend fun addSubcategory(subcategory: SubcategoryModel)

    suspend fun updateSubcategory(subcategory: SubcategoryModel)

    suspend fun removeSubcategory(subcategoryId: String)
}
