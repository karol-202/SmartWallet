package pl.karol202.smartwallet.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.IdDataSource
import pl.karol202.smartwallet.data.datasource.SubcategoryDataSource
import pl.karol202.smartwallet.data.model.SubcategoryModel
import pl.karol202.smartwallet.data.model.toEntity
import pl.karol202.smartwallet.data.model.toModel
import pl.karol202.smartwallet.domain.entity.Existing
import pl.karol202.smartwallet.domain.entity.New
import pl.karol202.smartwallet.domain.entity.Subcategory
import pl.karol202.smartwallet.domain.repository.SubcategoryRepository

class LocalSubcategoryRepository(private val subcategoryDataSource: SubcategoryDataSource,
                                 private val idDataSource: IdDataSource) : SubcategoryRepository
{
	override fun getSubcategory(subcategoryId: String) =
			subcategoryDataSource.getSubcategory(subcategoryId).map { it.toEntity() }

	override fun getSubcategoriesOfCategory(categoryId: String) =
			subcategoryDataSource.getSubcategoriesOfCategory(categoryId).map { it.map(SubcategoryModel::toEntity) }

	override suspend fun addSubcategory(subcategory: Subcategory<New>) =
			subcategoryDataSource.addSubcategory(subcategory.toModel(idDataSource.createRandomId()))

	override suspend fun updateSubcategory(subcategory: Subcategory<Existing>) =
			subcategoryDataSource.updateSubcategory(subcategory.toModel())

	override suspend fun removeSubcategory(subcategoryId: String) =
			subcategoryDataSource.removeSubcategory(subcategoryId)
}
