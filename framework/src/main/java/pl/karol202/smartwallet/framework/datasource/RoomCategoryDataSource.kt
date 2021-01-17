package pl.karol202.smartwallet.framework.datasource

import pl.karol202.smartwallet.data.datasource.CategoryDataSource
import pl.karol202.smartwallet.data.model.CategoryModel
import pl.karol202.smartwallet.framework.room.dao.CategoryDao
import pl.karol202.smartwallet.framework.room.entity.toRoomEntity

class RoomCategoryDataSource(private val categoryDao: CategoryDao) : CategoryDataSource
{
	override suspend fun addCategory(category: CategoryModel) =
			categoryDao.insert(category.toRoomEntity())

	override suspend fun updateCategory(category: CategoryModel) =
			categoryDao.update(category.toRoomEntity())

	override suspend fun removeCategory(categoryId: String) =
			categoryDao.delete(categoryId)
}