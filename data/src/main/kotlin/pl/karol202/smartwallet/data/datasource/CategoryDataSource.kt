package pl.karol202.smartwallet.data.datasource

import kotlinx.coroutines.flow.Flow
import pl.karol202.smartwallet.data.model.CategoryModel

interface CategoryDataSource
{
    val allCategories: Flow<List<CategoryModel>>

    suspend fun addCategory(category: CategoryModel)

    suspend fun updateCategory(category: CategoryModel)

    suspend fun removeCategory(categoryId: String)
}
