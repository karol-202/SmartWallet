package pl.karol202.smartwallet.data.repository

import pl.karol202.smartwallet.data.datasource.CategoryDataSource
import pl.karol202.smartwallet.data.datasource.IdDataSource
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.domain.entity.Category
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.repository.CategoryRepository

class LocalCategoryRepository(private val categoryDataSource: CategoryDataSource,
                              private val idDataSource: IdDataSource) : CategoryRepository
{
	override suspend fun addCategory(category: Category<New>) =
			categoryDataSource.addCategory(category.toModel(idDataSource.createRandomId()))

	override suspend fun updateCategory(category: Category<Existing>) =
			categoryDataSource.updateCategory(category.toModel())

	override suspend fun removeCategory(categoryId: String) = categoryDataSource.removeCategory(categoryId)
}
