package pl.karol202.smartwallet.data.datasource

import pl.karol202.smartwallet.data.model.CategoryModel

interface CategoryDataSource
{
    suspend fun addCategory(category: CategoryModel)

    suspend fun updateCategory(category: CategoryModel)

    suspend fun removeCategory(categoryId: String)
}
