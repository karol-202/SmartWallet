package pl.karol202.smartwallet.framework.datasource

import pl.karol202.smartwallet.data.datasource.SubcategoryDataSource
import pl.karol202.smartwallet.data.model.SubcategoryModel
import pl.karol202.smartwallet.framework.room.dao.SubcategoryDao
import pl.karol202.smartwallet.framework.room.entity.toRoomEntity

class RoomSubcategoryDataSource(private val subcategoryDao: SubcategoryDao) : SubcategoryDataSource
{
	override suspend fun addSubcategory(subcategory: SubcategoryModel) =
			subcategoryDao.insert(subcategory.toRoomEntity())

	override suspend fun updateSubcategory(subcategory: SubcategoryModel) =
			subcategoryDao.update(subcategory.toRoomEntity())

	override suspend fun removeSubcategory(subcategoryId: String) =
			subcategoryDao.delete(subcategoryId)
}
