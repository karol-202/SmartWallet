package pl.karol202.smartwallet.framework.datasource

import kotlinx.coroutines.flow.map
import pl.karol202.smartwallet.data.datasource.SubcategoryDataSource
import pl.karol202.smartwallet.data.model.SubcategoryModel
import pl.karol202.smartwallet.framework.room.dao.SubcategoryDao
import pl.karol202.smartwallet.framework.room.entity.SubcategoryRoomEntity
import pl.karol202.smartwallet.framework.room.entity.toModel
import pl.karol202.smartwallet.framework.room.entity.toRoomEntity

class RoomSubcategoryDataSource(private val subcategoryDao: SubcategoryDao) : SubcategoryDataSource
{
	override val allSubcategories = subcategoryDao.getAll().map { it.map(SubcategoryRoomEntity::toModel) }

	override fun getSubcategory(subcategoryId: String) =
			subcategoryDao.getById(subcategoryId).map { it?.toModel() }

	override suspend fun addSubcategory(subcategory: SubcategoryModel) =
			subcategoryDao.insert(subcategory.toRoomEntity())

	override suspend fun updateSubcategory(subcategory: SubcategoryModel) =
			subcategoryDao.update(subcategory.toRoomEntity())

	override suspend fun removeSubcategory(subcategoryId: String) =
			subcategoryDao.delete(subcategoryId)

	override suspend fun moveSubcategories(sourceCategoryId: String, destinationCategoryId: String) =
			subcategoryDao.moveBetweenCategories(sourceCategoryId, destinationCategoryId)
}
